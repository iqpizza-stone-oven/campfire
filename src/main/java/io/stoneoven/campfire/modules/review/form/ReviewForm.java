package io.stoneoven.campfire.modules.review.form;

import io.stoneoven.campfire.modules.review.Review;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@ToString
public class ReviewForm {

    @NotBlank
    @Length(max = 50)
    private String title;

    @NotBlank
    private String content;

    private String tags;

    public Review toEntity() {
        return Review.builder()
                .title(title)
                .content(content)
                .build();
    }

}
