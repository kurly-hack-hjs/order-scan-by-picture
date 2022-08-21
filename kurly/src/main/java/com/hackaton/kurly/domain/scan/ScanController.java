package com.hackaton.kurly.domain.scan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class ScanController {

    private final ScanService scanService;

    @GetMapping("/scan/sample")
    public ResponseEntity scanBySample() throws JsonProcessingException {
        //컬리에서 샘플용 이미지 세팅(컬리해커톤용, 상업용도 사용X)
        String imageUrl = "https://img-cf.kurly.com/shop/data/goods/1656479672431l0.jpg";
        List<String> result = scanService.contactToOcr(imageUrl);
        if (result.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }


}
