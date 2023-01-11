package com.hackaton.kurly.domain.order;

public enum ScanStatus {
    STANDBY, //대기중 (=미검증)
    RESET, //관리자에 의한 리셋(*실제로 목록에 저장되지는않습니다.)
    COMPLETE, //스캔 완료 (=검증 완료)
    SCAN_FAIL, //스캔 실패 (=재검증 필요)
    ORDER_CANCEL, // 주문 취소
    SCANNING, // (검증 진행중)
    COUNT_ERROR, // (수량 에러! 스냅샷에 나타납니다
    ETC; //기타 상태(=기타)
}
