package com.hackaton.kurly.domain.order;


import com.hackaton.kurly.domain.Item.ItemService;
import com.hackaton.kurly.domain.Item.dto.ItemsResponse;
import com.hackaton.kurly.domain.order.dto.PatchOrderRequest;
import com.hackaton.kurly.domain.order.dto.ReadOrderResponse;
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
import java.util.Optional;

@Api(value = "getOrderListToScan")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ItemService itemService;

    @ApiOperation(
            value = "주문정보 목록 조회"
            , notes = "(default page size = 20, sort = 주문번호.desc 필요에 따라 조정하세요) 필터링은 나중에 추가됩니다.")
    @GetMapping("/orders")
    public ResponseEntity getOrderList(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Order> orderList = orderService.findOrders(pageable);
        return ResponseEntity.ok(orderList);
    }

    @ApiOperation(
            value = "주문정보 단일 조회"
            , notes = "존재하지 않는 orderId를 보내게 되면 404 NOT FOUND가 뜹니다. [주문정보 목록 조회 api]를 이용해서 사용가능한 id를 확인하세요")
    @GetMapping("/order/{orderId}")
    public ResponseEntity getOneOrderWithOrderedItems(@PathVariable Long orderId) throws IOException {
        Optional<Order> wrapperOrder = orderService.findOneOrderById(orderId);
        if (! wrapperOrder.isPresent()){
            return ResponseEntity.notFound().build();
        }
        ItemsResponse items = itemService.findOneItemCartByOrderId(orderId);

        return ResponseEntity.ok(new ReadOrderResponse(wrapperOrder.get(), items));
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
}
