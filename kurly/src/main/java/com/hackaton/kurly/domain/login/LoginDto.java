package com.hackaton.kurly.domain.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginDto {
    private String centerName;
    private String loginId;
    private String password;
    private String staffCode;
}
