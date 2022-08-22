package com.hackaton.kurly.domain.login;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @ApiOperation(value = "로그인", notes = "현재 임시운영중으로 staffCode에 119 를 입력하면 뭐든지 이용가능")
    @PostMapping
    public ResponseEntity tempLogin(@RequestBody LoginDto loginDto){
        if (loginDto.getLoginId().equals("test001")){
            return ResponseEntity.ok(loginDto);
        }
        return ResponseEntity.badRequest().build();
    }
}
