package com.hackaton.kurly.domain.Item.dto;

import com.hackaton.kurly.domain.Item.dto.OrderedItemInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsResponse {

    @Schema(description = "주문번호")
    private Long orderId;

    @Schema(description = "쇼핑목록")
    private List<OrderedItemInfo> itemList;

}
