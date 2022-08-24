package com.hackaton.kurly.domain.login;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Value("${staff-key}")
    private String staffKey;

    @ApiOperation(value = "로그인", notes = "현재 임시운영중으로 staffCode에 122 를 입력하면 뭐든지 이용가능")
    @PostMapping("/login")
    public ResponseEntity tempLogin(@RequestBody LoginDto loginDto){
        if (loginDto.getStaffCode().equals(staffKey)){
            return ResponseEntity.ok(loginDto);
        }
        return ResponseEntity.badRequest().build();
    }
}
