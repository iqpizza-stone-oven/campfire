package io.stoneoven.campfire.modules.notification.event;

import io.stoneoven.campfire.modules.notification.Notification;
import io.stoneoven.campfire.modules.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationRepository repository;

    @EventListener
    public void sendNotification(NotificationEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("send notification [type: {}, receiver: {}]",
                    event.getType(), event.getReceiver().getEmail());
        }

        Notification notification = Notification.builder()
                .id(generateRandomId())
                .receiver(event.getReceiver())
                .title(event.getTitle())
                .message(event.getMessage())
                .link(event.getLink())
                .notificationType(event.getType())
                .build();
        repository.save(notification);
    }

    private String generateRandomId() {
        int randomNo = ThreadLocalRandom.current().nextInt(900000) + 100000;
        return String.format("%tY%<tm%<td%<tH-%d", new Date(), randomNo);
    }
}
