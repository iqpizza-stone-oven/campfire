package io.stoneoven.campfire.modules.review;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.account.UserAccount;
import io.stoneoven.campfire.modules.comment.Comment;
import io.stoneoven.campfire.modules.tag.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@ToString
@Builder @AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne(optional = false)
    private Account account;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String content;

    @OneToMany(mappedBy = "review")
    @ToString.Exclude
    private Set<Comment> comments;

    @ManyToMany
    @ToString.Exclude
    private Set<Tag> tags;

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void setWriter(Account writer) {
        this.account = writer;
    }

    public boolean isAuthor(UserAccount account) {
        return this.account.equals(account.getAccount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(getId(), review.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
