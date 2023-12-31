package io.stoneoven.campfire.modules.comment;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.account.CurrentAccount;
import io.stoneoven.campfire.modules.comment.form.CommentForm;
import io.stoneoven.campfire.modules.review.Review;
import io.stoneoven.campfire.modules.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        Comment comment = commentService.createNewComment(account, review, commentForm);
        review.addComment(comment);
        return "redirect:/review/" + URLEncoder.encode(review.getTitle(), StandardCharsets.UTF_8);
    }

    @GetMapping("/comment/{comment-id}/modify")
    public String modifyCommentForm(@CurrentAccount Account account,
                                @PathVariable("comment-id") long commentId, Model model) {
        Comment comment = commentService.getComment(commentId);
        if (!comment.isAccount(account)) {
            throw new IllegalArgumentException();
        }

        Review review = comment.getReview();
        CommentForm commentForm = new CommentForm();
        commentForm.setId(commentId);
        commentForm.setContent(comment.getContent());
        model.addAttribute(commentForm);
        model.addAttribute(comment);
        model.addAttribute("comments", review.getComments());
        model.addAttribute(review);
        model.addAttribute(account);
        return "review/view";
    }

    @PutMapping("/comment/{comment-id}/modify")
    public String modifyComment(@CurrentAccount Account account, CommentForm commentForm,
                                @PathVariable("comment-id") long commentId) {
        Comment comment = commentService.getComment(commentId);
        if (!comment.isAccount(account)) {
            throw new IllegalArgumentException();
        }

        commentService.updateComment(comment, commentForm.getContent());
        Review review = comment.getReview();
        return "redirect:/review/" + URLEncoder.encode(review.getTitle(), StandardCharsets.UTF_8);
    }

    @GetMapping("/comment/{comment-id}/delete")
    public String deleteComment(@CurrentAccount Account account,
                                @PathVariable("comment-id") long commentId) {
        Comment comment = commentService.getComment(commentId);
        if (!comment.isAccount(account)) {
            throw new IllegalArgumentException();
        }

        Review review = comment.getReview();
        review.removeComment(comment);
        commentService.removeComment(comment);
        return "redirect:/review/" + URLEncoder.encode(review.getTitle(), StandardCharsets.UTF_8);
    }

    @GetMapping("/comment/{comment-id}/sympathy")
    public String sympathyComment(@CurrentAccount Account account,
                                  @RequestParam boolean isIncrease, @PathVariable("comment-id") long commentId) {
        Comment comment = commentService.getComment(commentId);
        CommentSympathy sympathy;
        if (isIncrease) {
            comment.increaseSympathy();
            sympathy = CommentSympathy.INCREASE;
        }
        else {
            comment.decreaseSympathy();
            sympathy = CommentSympathy.DECREASE;
        }

        commentService.updateSympathy(account, sympathy, comment);
        Review review = comment.getReview();
        return "redirect:/review/" + URLEncoder.encode(review.getTitle(), StandardCharsets.UTF_8);
    }

    @GetMapping("/comment/{comment-id}/cancel-sympathy")
    public String removeSympathy(@CurrentAccount Account account,
                                  @RequestParam boolean isIncrease, @PathVariable("comment-id") long commentId) {
        Comment comment = commentService.getComment(commentId);
        // do opposite because it is cancel
        if (isIncrease) {
            comment.decreaseSympathy();
        }
        else {
            comment.increaseSympathy();
        }

        commentService.updateSympathy(account, null, comment);
        Review review = comment.getReview();
        return "redirect:/review/" + URLEncoder.encode(review.getTitle(), StandardCharsets.UTF_8);
    }

    @GetMapping("/comment/{comment-id}/check")
    public String checkComment(@CurrentAccount Account account,
                               @PathVariable("comment-id") long commentId) {
        Comment comment = commentService.getComment(commentId);
        Review review = comment.getReview();
        if (!review.isAuthor(account)) {
            throw new IllegalArgumentException("you're not writer");
        }

        commentService.commentSelection(comment);
        return "redirect:/review/" + URLEncoder.encode(review.getTitle(), StandardCharsets.UTF_8);
    }
}
