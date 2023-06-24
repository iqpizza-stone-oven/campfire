package io.stoneoven.campfire.modules.chat;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.account.AccountRepository;
import io.stoneoven.campfire.modules.notification.NotificationType;
import io.stoneoven.campfire.modules.notification.event.NotificationEvent;
import io.stoneoven.campfire.modules.review.Review;
import io.stoneoven.campfire.modules.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository repository;
    private final AccountRepository accountRepository;
    private final ApplicationEventPublisher eventPublisher;

    public Chat createForum(Account self, Chat chat) {
        Chat newChat = repository.save(chat);
        Review review = newChat.getReview();
        review.setForum(newChat);
        enrollFormNotification(self, newChat.getId(), review.getTags());
        return newChat;
    }

    private void enrollFormNotification(Account self, long chatId, Set<Tag> tags) {
        Iterable<Account> accounts = accountRepository.findAllByTagsIn(tags);
        for (Account account : accounts) {
            if (account.equals(self)) {
                continue;
            }

            eventPublisher.publishEvent(new NotificationEvent(
                    account, "/forum/" + chatId,
                    NotificationType.REVIEW_FORUM
            ));
        }
    }

    public Chat getChat(long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    public void deleteChat(Chat chat) {
        repository.delete(chat);
    }
}
