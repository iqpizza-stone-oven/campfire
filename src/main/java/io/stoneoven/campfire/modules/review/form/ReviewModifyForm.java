package io.stoneoven.campfire.modules.review.form;

import io.stoneoven.campfire.modules.review.Review;
import io.stoneoven.campfire.modules.tag.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.stream.Collectors;

@Data
@ToString
@AllArgsConstructor @NoArgsConstructor
public class ReviewModifyForm {

    @NotBlank
    @Length(max = 255)
    private String title;

    @NotBlank
    private String content;

    private String tags;

    public ReviewModifyForm(Review review) {
        this.title = review.getTitle();
        this.content = review.getContent();
        this.tags = review.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.joining(","));
    }
}
