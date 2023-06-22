package io.stoneoven.campfire.modules.comment;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.comment.form.CommentForm;
import io.stoneoven.campfire.modules.notification.NotificationType;
import io.stoneoven.campfire.modules.notification.event.NotificationEvent;
import io.stoneoven.campfire.modules.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CARepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public Comment createNewComment(Account account, Review review,
                                 CommentForm commentForm) {
        Comment comment = commentRepository.save(Comment.builder()
                .account(account)
                .content(commentForm.getContent())
                .review(review)
                .build());
        if (!comment.isAccount(account)) {
            eventPublisher.publishEvent(new NotificationEvent(
                    review.getAccount(),
                    "/review/" + URLEncoder.encode(review.getTitle(), StandardCharsets.UTF_8),
                    NotificationType.REVIEW_RECEIVE
            ));
        }

        return comment;
    }

    public Comment getComment(long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow();
    }

    public void updateSympathy(Account account, CommentSympathy sympathy,
                               Comment comment) {
        CommentAccount commentAccount = repository.findByAccountAndComment(account, comment)
                .orElse(CommentAccount.builder()
                        .account(account)
                        .comment(comment)
                        .sympathy(sympathy)
                        .build());
        if (sympathy != null) {
            if (commentAccount.getId() == null) {
                commentAccount = repository.save(commentAccount);
            }
            else {
                // do twice when change sympathy type
                if (sympathy == CommentSympathy.INCREASE) {
                    comment.increaseSympathy();
                }
                else {
                    comment.decreaseSympathy();
                }
            }

            CommentAccount ca = comment.addSympathy(commentAccount);
            if (ca != null) {
                ca.updateSympathy(sympathy);
                repository.save(ca);
            }
        }
        else {
            if (commentAccount.getId() == null) {
                return;
            }

            comment.deleteSympathy(commentAccount);
            repository.delete(commentAccount);
        }

        commentRepository.save(comment);
    }

    public void updateComment(Comment comment, String content) {
        comment.modifyContent(content);
        commentRepository.save(comment);
    }

    public void removeComment(Comment comment) {
        commentRepository.delete(comment);
    }

    public void commentSelection(Comment comment) {
        if (comment.isSelection()) {
            throw new IllegalArgumentException("already selected!");
        }

        comment.check();
        commentRepository.save(comment);
    }
}
