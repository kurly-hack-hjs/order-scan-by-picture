package com.hackaton.kurly.domain.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class MakeOrderDto {
    private List<Integer> itemIds;
    private List<Integer> itemCounts;
}
