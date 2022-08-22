package com.hackaton.kurly.domain.scan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanRequest {

    @Schema(description = "aws S3로 업로드하고 나온 imageUrl, 500kb이하 jpg여야합니다")
    private String imageUrl;
    @Schema(description = "주문 orderId")
    private Long orderId;
    @Schema(description = "지금 로그인중인 loginId")
    private String loginId;
}
