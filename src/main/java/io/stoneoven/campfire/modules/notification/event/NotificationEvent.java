package io.stoneoven.campfire.modules.notification.event;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 알림을 발생시키도록 하는 이벤트 클래스
 */
@Getter
@AllArgsConstructor
public class NotificationEvent {
    private Account receiver;
    private String title;
    private String message;
    private String link;
    private NotificationType type;

    public NotificationEvent(Account receiver, String link,
                             NotificationType type) {
        this.receiver = receiver;
        this.message = setDefaultMessage(type);
        this.link = link;
        this.type = type;
        this.title = setDefaultTitle(type);
    }

    private String setDefaultMessage(NotificationType type) {
        return switch (type) {
            case REVIEW_RECEIVE -> "리뷰 확인을 위해 클릭해주세요.";
            case REVIEW_REQUEST -> "코드 확인을 위해 클릭해주세요.";
            case REVIEW_SELECTED -> "당신의 코드리뷰가 채택되었습니다!";
            case REVIEW_FORUM -> "포럼 입장을 원하면 클릭해주세요.";
            default -> throw new IllegalStateException("System type must have message");
        };
    }
    private String setDefaultTitle(NotificationType type) {
        return switch (type) {
            case REVIEW_RECEIVE -> "누군가가 리뷰를 남겼습니다.";
            case REVIEW_REQUEST -> "관심있는 주제의 코드 리뷰 요청이 왔습니다.";
            case REVIEW_SELECTED -> "코드 리뷰";
            case REVIEW_FORUM -> "관심있는 주제의 코드 포럼이 생겼습니다.";
            default -> throw new IllegalStateException("System type must have title");
        };
    }
}
