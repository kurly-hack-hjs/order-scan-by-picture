package com.hackaton.kurly.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Order {

    private Long orderId;

    private ScanStatus scanStatus;

    private String updatedUser;

    private String memo;

    private OrderDetail orderDetail;

    private int quantity;

}
