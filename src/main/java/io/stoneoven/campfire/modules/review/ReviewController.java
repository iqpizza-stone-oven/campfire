package io.stoneoven.campfire.modules.review;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.account.CurrentAccount;
import io.stoneoven.campfire.modules.review.form.ReviewForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/new-review")
    public String newReviewForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute(new ReviewForm());
        return "review/review";
    }

    @PostMapping("/new-review")
    public String newReviewSubmit(@CurrentAccount Account account, ReviewForm reviewForm,
                                  Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return "review/review";
        }

        Review review = reviewService.createNewReview(reviewForm, account);
        return "redirect:/review/" + URLEncoder.encode(review.getTitle(), StandardCharsets.UTF_8);
    }

    @GetMapping("/review/{review-title}")
    public String viewReview(@CurrentAccount Account account, Model model,
                             @PathVariable("review-title") String title) {
        Review review = reviewService.getReview(URLDecoder.decode(title, StandardCharsets.UTF_8), account);
        model.addAttribute(account);
        model.addAttribute(review);
        return "review/view";
    }

    @GetMapping("/review/{review-id}/modify")
    public String modifyReviewForm(@CurrentAccount Account account, Model model,
                                   @PathVariable("review-id") long id) {
        Review review = reviewService.findReviewById(id);
        if (!review.isAuthor(account)) {
            model.addAttribute(account);
            model.addAttribute(review);
            return "review/view";
        }

        model.addAttribute(account);
        model.addAttribute(review);
        return "review/modify";
    }

    @PutMapping("/review/{review-id}/modify")
    public String modifyReview(@CurrentAccount Account account, ReviewForm reviewForm,
                               @PathVariable("review-id") long id,
                               Errors errors, Model model) {
        final Review review = reviewService.findReviewById(id);
        if (errors.hasErrors() || !review.isAuthor(account)) {
            model.addAttribute(account);
            model.addAttribute(review);
            return "redirect:/review/view";
        }

        final Review modifiedReview = reviewService.updateReview(review, reviewForm);
        model.addAttribute(account);
        model.addAttribute(modifiedReview);
        return "redirect:/review/view";
    }

    @DeleteMapping("/review/{review-id}")
    public String deleteReview(@CurrentAccount Account account,
                               @PathVariable("review-id") long id,
                               Model model) {
        final Review review = reviewService.findReviewById(id);
        if (!review.isAuthor(account)) {
            model.addAttribute(account);
            model.addAttribute(review);
            return "redirect:/review/view";
        }

        reviewService.deleteReview(review);
        model.addAttribute(account);
        return "redirect:/";
    }
}
