package com.hackaton.kurly.controller;


import com.hackaton.kurly.domain.order.Order;
import com.hackaton.kurly.domain.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "getOrderListToScan", tags = {"part-time", "order"})
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @ApiOperation(
            value = "주문정보 목록 조회"
            , notes = "(default page size = 20, sort = 주문번호.desc 필요에 따라 조정하세요) 필터링은 나중에 추가됩니다.")
    @GetMapping("/orders")
    public ResponseEntity getOrderList(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Order> orderList = orderService.findOrders(pageable);
        return ResponseEntity.ok(orderList);
    }

}
