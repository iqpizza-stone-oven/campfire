package io.stoneoven.campfire.modules.chat;

import io.stoneoven.campfire.modules.review.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {

    boolean existsByReview(Review review);

}
