package io.stoneoven.campfire.modules.comment;

import io.stoneoven.campfire.modules.account.Account;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor @NoArgsConstructor
public class CommentAccount {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Account account;

    @ManyToOne(optional = false)
    private Comment comment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommentSympathy sympathy;

    public void updateSympathy(CommentSympathy sympathy) {
        this.sympathy = sympathy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentAccount that = (CommentAccount) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
