package com.hackaton.kurly.domain.snapshot;

import com.hackaton.kurly.domain.Item.dto.OrderedItemInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShotWithOrder {
    private Long orderId;
    private int tryCount;
    private List<OrderedItemInfo> itemList;
    private boolean isSatisfied;
}
