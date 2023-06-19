package io.stoneoven.campfire.modules.review;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.account.CurrentAccount;
import io.stoneoven.campfire.modules.review.form.ReviewForm;
import io.stoneoven.campfire.modules.review.form.ReviewModifyForm;
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

        reviewForm.setTitle(reviewForm.getTitle().trim());
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
        model.addAttribute("id", id);
        model.addAttribute(new ReviewModifyForm(review));
        return "review/modify";
    }

    @PutMapping("/review/{review-id}/modify")
    public String modifyReview(@CurrentAccount Account account, ReviewModifyForm reviewForm,
                               @PathVariable("review-id") long id, Errors errors) {
        final Review review = reviewService.findReviewById(id);
        if (errors.hasErrors() || !review.isAuthor(account)) {
            return "redirect:/";
        }

        Review modifiedReview = reviewService.updateReview(review, reviewForm);
        return "redirect:/review/"
                + URLEncoder.encode(modifiedReview.getTitle(), StandardCharsets.UTF_8);
    }

    @GetMapping("/review/{review-id}/leave")
    public String deleteReview(@CurrentAccount Account account,
                               @PathVariable("review-id") long id) {
        final Review review = reviewService.findReviewById(id);
        if (!review.isAuthor(account)) {
            return "redirect:/";
        }

        reviewService.deleteReview(review);
        return "redirect:/";
    }
}
