package com.hackaton.kurly.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResult {

    private Long orderId;

    private boolean item1;
    private boolean item2;
    private boolean item3;
    private boolean item4;
    private boolean item5;
    private boolean item6;

}
