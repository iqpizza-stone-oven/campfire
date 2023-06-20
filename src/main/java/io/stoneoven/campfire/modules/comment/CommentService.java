package io.stoneoven.campfire.modules.comment;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.comment.form.CommentForm;
import io.stoneoven.campfire.modules.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void createNewComment(Account account, Review review,
                                 CommentForm commentForm) {
        commentRepository.save(Comment.builder()
                .account(account)
                .content(commentForm.getContent())
                .review(review)
                .build());
    }

    public Comment getComment(long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow();
    }

}
