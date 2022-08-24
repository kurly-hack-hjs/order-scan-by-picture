package com.hackaton.kurly.domain.order;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.hackaton.kurly.domain.Item.Item;
import com.hackaton.kurly.domain.Item.ItemService;
import com.hackaton.kurly.domain.Item.dto.ItemsResponse;
import com.hackaton.kurly.domain.Item.dto.OrderedItemInfo;
import com.hackaton.kurly.domain.itemCart.CartSnapshot;
import com.hackaton.kurly.domain.itemCart.ItemCartService;
import com.hackaton.kurly.domain.itemCart.dto.SnapshotResponse;
import com.hackaton.kurly.domain.order.dto.MakeOrderDto;
import com.hackaton.kurly.domain.order.dto.PatchOrderRequest;
import com.hackaton.kurly.domain.order.dto.ReadOrderResponse;
import com.hackaton.kurly.domain.snapshot.ShotWithOrder;
import com.hackaton.kurly.domain.snapshot.Snapshot;
import com.hackaton.kurly.domain.snapshot.SnapshotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Api(value = "getOrderListToScan")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ItemService itemService;
    private final ItemCartService itemCartService;
    private final SnapshotService snapshotService;
    private final ObjectMapper objectMapper;

    @ApiOperation(
            value = "주문정보 목록 조회"
            , notes = "(default page size = 50, sort = 주문번호.desc 필요에 따라 조정하세요) 필터링은 나중에 추가됩니다.")
    @GetMapping("/orders")
    public ResponseEntity getOrderList(@PageableDefault(size = 50, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Order> orderList = orderService.findOrders(pageable);
        return ResponseEntity.ok(orderList);
    }

    @ApiOperation(
            value = "주문정보 단일 조회"
            , notes = "존재하지 않는 orderId를 보내게 되면 404 NOT FOUND가 뜹니다. [주문정보 목록 조회 api]를 이용해서 사용가능한 id를 확인하세요")
    @GetMapping("/order/{orderId}")
    public ResponseEntity getOneOrderWithOrderedItems(@PathVariable Long orderId) throws IOException {
        // 기본 오더 가져옴
        Optional<Order> wrapperOrder = orderService.findOneOrderById(orderId);
        if (! wrapperOrder.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Order order = wrapperOrder.get();


        //기본 origin 주문 아이템 목록가져옴
        SnapshotResponse item = itemCartService.findCartSnapshotById(orderId);
        ItemsResponse items = new ItemsResponse(orderId, item.getItemList(), item.getTotalItemCount());


        // 스냅샷 정보들 가져옴
        List<ShotWithOrder> snapshotList = new ArrayList<>();
        List<Snapshot> snapshots = snapshotService.findBySnapshot(orderId);
        for (Snapshot snapshot : snapshots){
            List<OrderedItemInfo> snapshotByItem = Arrays.asList(objectMapper.readValue(snapshot.getSnapshots(), OrderedItemInfo[].class));
            snapshotList.add(new ShotWithOrder(orderId,  snapshot.getTryCount(),snapshotByItem ,snapshot.isSatisfied()));
        }



        return ResponseEntity.ok(new ReadOrderResponse(order, items, snapshotList, order.getTryCount()));
    }


    @ApiOperation(
            value = "주문정보 스캔결과 업데이트 또는 (관리자) 주문정보 스캔결과 리셋"
            , notes = "존재하지 않는 orderId를 보내게 되면 404 NOT FOUND가 뜹니다. swagger request body의 Schema, [주문정보 목록 조회 api]를 이용해서 사용가능한 id를 확인하세요. 관리자모드에서 RESET으로 보낼시에 tryConunt가 초기화되고 RESET이가 됩니다.")
    @PatchMapping("/order/{orderId}/scan_result")
    public ResponseEntity patchOrderToNewStatus (@RequestBody PatchOrderRequest request){
        Optional<Order> wrapperOrder = orderService.findOneOrderById(request.getOrderId());
        if (! wrapperOrder.isPresent()){
            return ResponseEntity.notFound().build();
        }
        orderService.update(wrapperOrder.get(), request);

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/order/new")
    public ResponseEntity makeOrder(@RequestBody MakeOrderDto makeOrderDto){
        List<Integer> itemIdList = makeOrderDto.getItemIds();
        List<Integer> countList = makeOrderDto.getItemCounts();
        int itemKindCount = itemIdList.size();

        int totalCount=0;
        for(int count: countList){
            totalCount= totalCount + count;
        }

        Order order = orderService.save(new Order(totalCount));
        List<OrderedItemInfo> list = new ArrayList<>();
        for(int i = 0; i<itemKindCount ; i++) {
             Item item = itemService.findItemById(itemIdList.get(i));
             OrderedItemInfo orderedItemInfo = new OrderedItemInfo(item.getId(), item.getName(), countList.get(i));
             list.add(orderedItemInfo);
        }
        String string= new Gson().toJson(list).toString();
        itemCartService.save(new CartSnapshot(order.getId(), string, string, string, 0));
        snapshotService.save(new Snapshot(order.getId(), 0, string, null, false));
            return ResponseEntity.ok(order);
    }
}
