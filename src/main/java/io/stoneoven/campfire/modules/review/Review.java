package io.stoneoven.campfire.modules.review;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.account.UserAccount;
import io.stoneoven.campfire.modules.chat.Chat;
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

    @Builder.Default
    @Column(nullable = false)
    private boolean commentSelected = false;

    @Builder.Default
    @Column(nullable = false)
    private boolean forumOpened = false;

    @OneToOne(mappedBy = "review")
    private Chat forum;

    @ManyToMany
    @ToString.Exclude
    private Set<Tag> tags;

    public void setDescriptions(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void setForum(Chat forum) {
        this.forumOpened = (forum != null);
        this.forum = forum;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
    }

    public void setWriter(Account writer) {
        this.account = writer;
    }

    public boolean isAuthor(UserAccount account) {
        return isAuthor(account.getAccount());
    }

    public boolean isAuthor(Account account) {
        return this.account.equals(account);
    }

    public void selectComment() {
        this.commentSelected = true;
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
