package com.hackaton.kurly.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;




@RestController
@RequiredArgsConstructor
public class ScanController {
    private ApiKey apiKey;


    @Value("${api-key}")
    @GetMapping("/scan")
    public ResponseEntity scan(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-OCR-SECRET", "ocr키값");

        String json ="{\"images\":[{\"format\":\"jpg\",\"name\":\"demo\",\"url\":\"실제이미지url\"}],\"requestId\":\"guide-json-demo\",\"version\":\"V2\",\"timestamp\":1584062336793}";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(json,headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://uo3w7cyx2k.apigw.ntruss.com/custom/v1/17834/61a3bd339557ae2268b281c268ba43e511e4a93e5667b1e44aabb37306535dc6/general",
                request , String.class);

        return response;
    }


}
