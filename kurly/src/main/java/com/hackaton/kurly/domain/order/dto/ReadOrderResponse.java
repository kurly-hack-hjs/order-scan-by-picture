package com.hackaton.kurly.domain.order.dto;

import com.hackaton.kurly.domain.Item.dto.ItemsResponse;
import com.hackaton.kurly.domain.order.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class ReadOrderResponse {
    private Order order;
    private ItemsResponse items;
    private ItemsResponse recentSnapshot;
    private int tryCount;
}
