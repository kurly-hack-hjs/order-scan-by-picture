package com.hackaton.kurly.domain.Item;

import com.hackaton.kurly.domain.Item.dto.ItemsResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class ItemCartController {

    private final ItemService itemService;
    @ApiOperation(
            value = "주문정보가 갖고 있는 상품 리스트 확인(임시운영중)"
            , notes = "(baseurl~~)/items/{orderId}을 입력하면 orderId가 갖고있는 상품리스트를(with http.200) 뿌려줍니다. (* 주문 내역이 없거나 잘못된 접근의 경우엔 404 NO FOUND)")
    @GetMapping("/items/{orderId}")
    public ResponseEntity getItemListAboutOrderThatHasOrderId (@PathVariable Long orderId) throws IOException {
        ItemsResponse itemsResponse = itemService.findOneItemCartByOrderId(orderId);
        if (itemsResponse.getItemList().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(itemsResponse);
    }
}
