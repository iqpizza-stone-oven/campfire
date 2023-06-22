package io.stoneoven.campfire.modules.review;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.account.AccountRepository;
import io.stoneoven.campfire.modules.notification.NotificationType;
import io.stoneoven.campfire.modules.notification.event.NotificationEvent;
import io.stoneoven.campfire.modules.review.form.ReviewForm;
import io.stoneoven.campfire.modules.review.form.ReviewModifyForm;
import io.stoneoven.campfire.modules.tag.Tag;
import io.stoneoven.campfire.modules.tag.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final AccountRepository accountRepository;
    private final ReviewRepository reviewRepository;
    private final TagService tagService;
    private final ApplicationEventPublisher eventPublisher;

    public Review createNewReview(ReviewForm reviewForm, Account account) {
        Review review = reviewForm.toEntity();
        review.setWriter(account);
        if (reviewForm.getTags() != null) {
            review.setTags(tagService.convertToTags(reviewForm.getTags()));
        }
        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public Review getReview(String title) {
        return reviewRepository.findByTitle(title)
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public Review findReviewById(long id) {
        return reviewRepository.findById(id)
                .orElseThrow();
    }

    public Review updateReview(Review review, ReviewModifyForm reviewForm) {
        review.setDescriptions(reviewForm.getTitle(), review.getContent());
        review.setTags(tagService.convertToTags(reviewForm.getTags()));
        return reviewRepository.save(review);
    }

    public void deleteReview(Review review) {
        reviewRepository.delete(review);
    }
}
