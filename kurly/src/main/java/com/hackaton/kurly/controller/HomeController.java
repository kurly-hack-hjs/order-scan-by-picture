package com.hackaton.kurly.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
    @ApiOperation(
            value = "home"
            , notes = "AWS Beanstalk 상태 유지(root를 비워두면 계속 경고가 뜨길래 채워둡니다. 실제로는 사용할 곳이 없습니다..")
    @GetMapping("/")
    public ResponseEntity goHomeWithGET(){
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/")
    public ResponseEntity goHomeWithPOST(){
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/")
    public ResponseEntity goHomeWithPUT(){
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/")
    public ResponseEntity goHomeWithDelete(){
        return ResponseEntity.noContent().build();
    }
}
