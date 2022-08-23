package com.hackaton.kurly.domain.snapshot;

import com.hackaton.kurly.domain.Item.dto.OrderedItemInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShotDto {
    private long orderId;
    private int tryCount;
    private List<OrderedItemInfo> snapshots;



}
