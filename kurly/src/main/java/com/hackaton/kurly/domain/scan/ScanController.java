package com.hackaton.kurly.domain.scan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hackaton.kurly.domain.Item.Item;
import com.hackaton.kurly.domain.Item.ItemService;
import com.hackaton.kurly.domain.Item.dto.ItemsResponse;
import com.hackaton.kurly.domain.scan.dto.ScanRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class ScanController {
    private final ItemService itemService;
    private final ScanService scanService;


    @GetMapping("/scan/sample")
    public ResponseEntity scanBySample() throws JsonProcessingException {
        //컬리에서 샘플용 이미지 세팅(컬리해커톤용, 상업용도 사용X)
        String imageUrl = "https://img-cf.kurly.com/cdn-cgi/image/width=676,format=auto/shop/data/goods/1637154205701l0.jpg";// "https://img-cf.kurly.com/shop/data/goods/1656479672431l0.jpg";
        List<String> result = scanService.contactToOcr(imageUrl);
        if (result.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @ApiOperation(
            value = "이미지 텍스트 추출후, 주문과 대조"
            , notes = "{ \"orderId\":1231246,\n" +
            "\"imageUrl\":\"https://img-cf.kurly.com/shop/data/goods/1656479672431l0.jpg\"\n" +
            "}" +
            "로 테스트해보세요 ㅎㅎ... 제가 아직 테스트셋을 별로 안만들어놔서..ㅎㅎ;; (※ respone로 검증된 item정보들을 보여줍니다) ")
    @PostMapping("/scan")
    public ResponseEntity scanWithOrderIdAndImageUrl(@RequestBody ScanRequest scanRequest) throws IOException {
        ItemsResponse orderedItems = itemService.findOneItemCartByOrderId(scanRequest.getOrderId());
        List<String> textsFromImage = scanService.contactToOcr(scanRequest.getImageUrl());
        List<Item> result =scanService.compare2DataSetForScan(orderedItems, textsFromImage);
        return ResponseEntity.ok(result);
    }


}
