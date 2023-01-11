package com.hackaton.kurly.domain.order.dto;

import com.hackaton.kurly.domain.Item.dto.ItemsResponse;
import com.hackaton.kurly.domain.order.Order;
import com.hackaton.kurly.domain.snapshot.ShotWithOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class ReadOrderResponse {
    private Order order;
    private ItemsResponse items;
    private List<ShotWithOrder> snapshotList;
    private int tryCount;
}
