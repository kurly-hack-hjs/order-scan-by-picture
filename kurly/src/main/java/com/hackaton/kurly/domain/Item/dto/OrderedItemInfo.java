package com.hackaton.kurly.domain.Item.dto;

import com.hackaton.kurly.domain.order.ScanStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedItemInfo {
    @Schema(description = "상품ID")
    private UUID id;

    @Schema(description = "상품이름")
    private String name;

    @Schema(description = "수량")
    private int count;

    @Schema(description = "검증 상태")
    @Enumerated(EnumType.STRING)
    private ScanStatus scanStatus;

    public OrderedItemInfo(UUID id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.scanStatus = ScanStatus.STANDBY;
    }
}
