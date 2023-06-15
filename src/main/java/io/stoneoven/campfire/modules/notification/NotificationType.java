package io.stoneoven.campfire.modules.notification;

public enum NotificationType {
    /**
     * 시스템에서 전송된 알림
     */
    SYSTEM,

    /**
     * 리뷰 요청으로 인해 전송된 알림
     */
    REVIEW_REQUEST,

    /**
     * 리뷰 요청으로 인해 댓글이 달렸을 때 발생되는 알림
     */
    REVIEW_RECEIVE
}
