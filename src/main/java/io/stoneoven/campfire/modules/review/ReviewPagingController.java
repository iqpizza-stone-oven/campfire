package io.stoneoven.campfire.modules.review;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewPagingController {

    private final ReviewRepository reviewRepository;

    @GetMapping("/review-page")
    public ModelAndView findReviewsByScroll(
            @RequestParam(value = "review-id", defaultValue = "0") long lastReviewId) {
        PageRequest request = PageRequest.ofSize(10);
        List<Review> reviews = reviewRepository.findAllByIdGreaterThan(request, lastReviewId)
                .toList();
        ModelAndView model = new ModelAndView("review/review-paging");
        model.addObject("reviews", reviews);
        return model;
    }
}
