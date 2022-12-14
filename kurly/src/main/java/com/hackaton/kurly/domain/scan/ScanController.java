package com.hackaton.kurly.domain.scan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.hackaton.kurly.domain.Item.Item;
import com.hackaton.kurly.domain.Item.ItemService;
import com.hackaton.kurly.domain.Item.dto.ItemsResponse;
import com.hackaton.kurly.domain.Item.dto.OrderedItemInfo;
import com.hackaton.kurly.domain.itemCart.ItemCartService;
import com.hackaton.kurly.domain.itemCart.dto.SnapshotResponse;
import com.hackaton.kurly.domain.order.Order;
import com.hackaton.kurly.domain.order.OrderService;
import com.hackaton.kurly.domain.order.ScanStatus;
import com.hackaton.kurly.domain.order.dto.PatchOrderRequest;
import com.hackaton.kurly.domain.scan.dto.ScanRequest;
import com.hackaton.kurly.domain.scan.dto.ScanResultResponse;
import com.hackaton.kurly.domain.snapshot.ShotDto;
import com.hackaton.kurly.domain.snapshot.Snapshot;
import com.hackaton.kurly.domain.snapshot.SnapshotPk;
import com.hackaton.kurly.domain.snapshot.SnapshotRepository;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class ScanController {
    private final OrderService orderService;
    private final ItemService itemService;
    private final ScanService scanService;
    private final ScanLogService scanLogService;
    private final ItemCartService itemCartService;
    private final SnapshotRepository snapshotRepository;
    private final ObjectMapper mapper;
    private int firstTry = 1;
    @GetMapping("/scan/sample")
    public ResponseEntity scanBySample() throws JsonProcessingException {
        //???????????? ????????? ????????? ??????(??????????????????, ???????????? ??????X)
        String imageUrl = "https://img-cf.kurly.com/cdn-cgi/image/width=676,format=auto/shop/data/goods/1637154205701l0.jpg";// "https://img-cf.kurly.com/shop/data/goods/1656479672431l0.jpg";
        List<String> result = scanService.contactToOcr(imageUrl);
        if (result.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @ApiOperation(
            value = "????????? ????????? ?????????, ????????? ??????"
            , notes = "{ \"orderId\":1231246,\n" +
            "\"imageUrl\":\"https://img-cf.kurly.com/shop/data/goods/1656479672431l0.jpg\"\n" +
            "}" +
            "????????? ???????????? ????????? ????????? ????????????(????????? ????????????)??? ????????????????????? ??????... ?????? ?????? ??????????????? ?????? ??????????????????..??????;; (??? respone??? ????????? item???????????? ???????????????) ")
    @PostMapping("/scan")
    public ResponseEntity scanWithOrderIdAndImageUrl(@RequestBody ScanRequest scanRequest) throws IOException {
        Order order= orderService.findOneOrderById(scanRequest.getOrderId()).get();
        if(scanRequest.getTryCount() == firstTry) { //??? ???????????? ?????? ??????
            PatchOrderRequest request = new PatchOrderRequest(scanRequest.getOrderId(), ScanStatus.SCANNING, scanRequest.getLoginId(), null);
            order.updateStatus(request);
        }
        order.setTryCount(scanRequest.getTryCount());
        orderService.save(order);
        ItemsResponse originItems = itemService.findOneItemCartByOrderId(order.getId());

        SnapshotResponse orderedItems = itemCartService.findCartSnapshotById(scanRequest.getOrderId());
        List<String> textsFromImage = scanService.contactToOcr(scanRequest.getImageUrl());
        List<Item> foundItems =scanService.compare2DataSetForScan(orderedItems, textsFromImage);
        ScanLog scanLog = new ScanLog(scanRequest.getLoginId(), scanRequest.getOrderId(),
                new Gson().toJson(foundItems) ,scanRequest.getImageUrl(), scanRequest.getTryCount());
         scanLogService.saveScanLogs(scanLog);
        orderedItems= itemCartService.checkNextStatus(orderedItems, foundItems, scanRequest);
        List<Snapshot> snapshotList = snapshotRepository.findByOrderId(scanRequest.getOrderId());
        Snapshot thisSnapshot = snapshotList.get(scanRequest.getTryCount());
        List<OrderedItemInfo> arrays = Arrays.asList(mapper.readValue(thisSnapshot.getSnapshots(), OrderedItemInfo[].class));
        /*List<ShotDto> shotDtos = new ArrayList<>();
        for(Snapshot snapshot:snapshots){
            List<OrderedItemInfo> arrays = Arrays.asList(mapper.readValue(snapshot.getSnapshots(), OrderedItemInfo[].class));
            shotDtos.add(new ShotDto(snapshot.getOrderId(),snapshot.getTryCount(), arrays));
        }*/

        ShotDto shotDto = new ShotDto(thisSnapshot.getOrderId(),thisSnapshot.getTryCount(), arrays);
        return ResponseEntity.ok(new ScanResultResponse(order,foundItems, originItems, shotDto, scanRequest.getTryCount()));
    }

    @ApiOperation( value = "(???????????????) OCR?????? API??? ????????? User??? scanLog ??????"
            , notes = "?????? 50??? ??????(size??? ???????????????), id????????????  ")
    @GetMapping("/scan/logs")
    public ResponseEntity getScanLogs(@PageableDefault(size = 50, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) throws IOException {
        Page<ScanLog> scanLogs = scanLogService.getScanLogs(pageable);
        return ResponseEntity.ok(scanLogs);
    }

}
