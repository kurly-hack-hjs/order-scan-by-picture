package com.hackaton.kurly.domain.itemCart.dto;

import com.hackaton.kurly.domain.Item.dto.OrderedItemInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnapshotResponse {

    @Schema(description = "주문번호")
    private Long orderId;

    @Schema(description = "쇼핑목록")
    private List<OrderedItemInfo> itemList;

    @Schema(description = "스냅샷 상태")
    private List<OrderedItemInfo> snapshot;

    @Schema(description = "스캔시도하고 남은 목록들")
    private List<OrderedItemInfo> restList;

    @Schema(description = "물품 총갯수..?")
    private int totalItemCount;

    @Schema(description = "시도한 횟수")
    private int tryCount;

    public SnapshotResponse(Long orderId, List<OrderedItemInfo> itemList, List<OrderedItemInfo> snapshot, List<OrderedItemInfo> restList, int size) {
        this.itemList = itemList;
        this.snapshot = snapshot;
        this.restList = restList;
        this.totalItemCount = size;
    }

    public void injectNextStatusFrom(List<OrderedItemInfo> stock){
        int itemListLength= stock.size();
        for (int i = 0; i<itemListLength; i++){
            //변경되게된 상태들만 snapshot에 copy해준다..
            this.snapshot.get(i).setScanStatus(stock.get(i).getScanStatus());
        }
    }
}
