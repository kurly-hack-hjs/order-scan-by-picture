package com.hackaton.kurly.domain.order.dto;

import com.hackaton.kurly.domain.order.ScanStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PatchOrderRequest {
    @Schema(description = "업데이트하고자 하는 주문의 orderId")
    private Long orderId;
    @Schema(description = "검증성공or 실패후에 새로 변경하고자 하는 상태값 [STANDBY, //대기중\n" +
            "    COMPLETE, //스캔 완료\n" +
            "    SCAN_FAIL, //스캔 실패\n" +
            "    ORDER_CANCEL, // 주문 취소\n" +
            "    SCANNING, // (누군가 진행중)\n" +
            "    ETC; //기타 상태]")
    private ScanStatus newOrderScanStatus;
    @Schema(description = "현재 로그인중인 아이디")
    private String loginId;
    @Schema(description = "실패시 남길 관리자 메모(성공시에는 적지 않고 넘어가도된다.)")
    private String memo;
}
