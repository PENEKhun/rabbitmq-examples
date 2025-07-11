package kr.huni.signup.enums;

public enum MessageQueueStatus {
    INIT, // 초기 값
    SEND_SUCCESS, // 이벤트 발행 성공
    SEND_FAIL, // 이벤트 발행 실패
}
