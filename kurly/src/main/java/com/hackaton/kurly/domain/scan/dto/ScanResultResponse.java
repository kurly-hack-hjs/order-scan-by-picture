package com.hackaton.kurly.domain.scan.dto;

import com.hackaton.kurly.domain.Item.Item;
import com.hackaton.kurly.domain.itemCart.dto.SnapshotResponse;
import com.hackaton.kurly.domain.order.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanResultResponse {
    private Order order;
    private List<Item> foundItemsFromPicture;
    private SnapshotResponse detailOrder;
    private int tryCount;
}
