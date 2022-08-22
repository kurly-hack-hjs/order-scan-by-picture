package com.hackaton.kurly.domain.order;

public enum ScanStatus {
    STANDBY, //대기중
    COMPLETE, //스캔 완료
    SCAN_FAIL, //스캔 실패
    ORDER_CANCEL, // 주문 취소
    SCANNING, // (누군가 진행중)
    ETC; //기타 상태
}
