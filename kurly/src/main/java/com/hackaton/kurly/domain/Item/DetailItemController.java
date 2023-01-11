package com.hackaton.kurly.domain.Item;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class DetailItemController {

    private final ItemService itemService;
    @ApiOperation(
            value = "(관리자모드) OCR검증에 사용되는 ItemScoreBoard 출력"
            , notes = "기본 50개 출력(size로 조절하세요), id오름차순 /// key의 9999는 무효값입니다. 근데 그냥 일단 출력하셔도 돼요(제가 아직 백엔드에서 핸들링을 덜함) ")
    @GetMapping("/item/score_board")
    public ResponseEntity getItemListAboutOrderThatHasOrderId (@PageableDefault(size = 50, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) throws IOException {
        Page<DetailItem> items = itemService.findDetailItems(pageable);
        if (items.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(items);
    }
}
