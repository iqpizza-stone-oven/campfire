package io.stoneoven.campfire.modules.chat;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.account.CurrentAccount;
import io.stoneoven.campfire.modules.review.Review;
import io.stoneoven.campfire.modules.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ReviewService reviewService;
    private final ChatRepository repository;
    private final ChatService service;

    @GetMapping("/review-chat/{review-id}")
    public String createForum(@CurrentAccount Account account, @PathVariable("review-id") long id) {
        Review review = reviewService.findReviewById(id);
        boolean isExists = repository.existsByReview(review);
        if (isExists) {
            throw new IllegalArgumentException("이미 존재하는 방입니다.");
        }

        Chat chat = Chat.builder()
                .destination("/topic/" + id)
                .review(review)
                .build();
        Chat savedChat = service.createForum(account, chat);
        return "redirect:/forum/" + savedChat.getId();
    }

    @GetMapping("/forum/{forum-id}")
    public String forumForm(@CurrentAccount Account account,
                            @PathVariable("forum-id") long id, Model model) {
        Chat chat = service.getChat(id);
        model.addAttribute(account);
        model.addAttribute("topic", chat.getDestination());
        return "chat/chat";
    }
}
