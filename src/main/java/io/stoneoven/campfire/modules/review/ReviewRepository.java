package io.stoneoven.campfire.modules.review;

import io.stoneoven.campfire.modules.account.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Slice<Review> findAllByIdGreaterThan(Pageable pageable, Long id);

    Optional<Review> findByTitle(String title);

}
