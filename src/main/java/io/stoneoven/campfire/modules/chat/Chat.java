package io.stoneoven.campfire.modules.chat;

import io.stoneoven.campfire.modules.review.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Chat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String destination;

    @OneToOne(optional = false)
    private Review review;

}
