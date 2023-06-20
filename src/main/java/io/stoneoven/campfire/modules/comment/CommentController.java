package io.stoneoven.campfire.modules.comment;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.account.CurrentAccount;
import io.stoneoven.campfire.modules.comment.form.CommentForm;
import io.stoneoven.campfire.modules.review.Review;
import io.stoneoven.campfire.modules.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final ReviewService reviewService;
    private final CommentService commentService;

    @PostMapping("/review/{review-id}/comment")
    public String addComment(@CurrentAccount Account account,
                             @PathVariable("review-id") long reviewId, CommentForm commentForm) {
        Review review = reviewService.findReviewById(reviewId);
        commentService.createNewComment(account, review, commentForm);
        return "redirect:/review/" + URLEncoder.encode(review.getTitle(), StandardCharsets.UTF_8);
    }
}
