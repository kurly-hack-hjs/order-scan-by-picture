package com.hackaton.kurly.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {

    private Long orderId;

    private UUID item1;
    private UUID item2;
    private UUID item3;
    private UUID item4;
    private UUID item5;

    private OrderResult orderResult;

}
