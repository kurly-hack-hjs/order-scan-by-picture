package com.hackaton.kurly;


import com.hackaton.kurly.order.Order;
import com.hackaton.kurly.order.OrderDetail;
import com.hackaton.kurly.order.OrderResult;
import com.hackaton.kurly.order.ScanStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
public class TestController {

    @GetMapping("/order/a_order_id")
    public ResponseEntity hi(){
        Long orderId =2362340021L;

        OrderResult result = OrderResult.builder().orderId(orderId).item1(true)
                .item2(true).item3(true).item4(false).item5(true).build();

        OrderDetail detail = OrderDetail.builder()
                .orderId(orderId)
                .item1(UUID.randomUUID())
                .item2(UUID.randomUUID())
                .item3(UUID.randomUUID())
                .item4(UUID.randomUUID())
                .item5(UUID.randomUUID())
                .orderResult(result).build();

        Order order = Order.builder()
                .orderId(orderId)
                .scanStatus(ScanStatus.SCAN_FAIL)
                .updatedUser("테스트아이디")
                .memo("미검증오류테스트중입니다 스탠바이는 스캔대기중")
                .orderDetail(detail)
                .build();

        return ResponseEntity.ok(order);
    }
}
