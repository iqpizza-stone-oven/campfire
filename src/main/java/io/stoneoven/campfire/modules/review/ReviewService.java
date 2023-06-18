package io.stoneoven.campfire.modules.review;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.review.form.ReviewForm;
import io.stoneoven.campfire.modules.tag.Tag;
import io.stoneoven.campfire.modules.tag.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final TagService tagService;

    public Review createNewReview(ReviewForm reviewForm, Account account) {
        Review review = reviewForm.toEntity();
        review.setWriter(account);
        if (reviewForm.getTags() != null) {
            List<String> rawTags = Arrays.stream(reviewForm.getTags().split(",")).toList();
            Set<Tag> tags = rawTags.stream().map(tagService::findOrCreateNew)
                    .collect(Collectors.toSet());
            review.setTags(tags);
        }
        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public Review getReview(String title, Account account) {
        return reviewRepository.findByAccountAndTitle(account, title)
                .orElseThrow();
    }
}
