package io.stoneoven.campfire.modules.comment;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.account.UserAccount;
import io.stoneoven.campfire.modules.review.Review;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Builder @AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String content;

    @Builder.Default
    @Column(nullable = false)
    private short sympathy = 0;

    @Builder.Default
    @Column(nullable = false)
    private boolean selection = false;

    @ManyToOne(optional = false)
    private Review review;

    @ManyToOne(optional = false)
    private Account account;

    @Builder.Default
    @OneToMany(mappedBy = "comment")
    private Set<CommentAccount> sympathyUsers = new HashSet<>();

    public void increaseSympathy() {
        ++this.sympathy;
    }

    public void decreaseSympathy() {
        --this.sympathy;
    }

    public boolean checkAlreadySympathy(CommentAccount account, long id) {
        return sympathyUsers.contains(account);
    }

    public boolean checkIncreaseByAccount(Account account, long id) {
        return sympathyUsers.stream()
                .anyMatch(ca -> ca.getAccount().equals(account) && this.id == id
                        && ca.getSympathy() == CommentSympathy.INCREASE);
    }

    public boolean checkDecreaseByAccount(Account account, long id) {
        return sympathyUsers.stream()
                .anyMatch(ca -> ca.getAccount().equals(account) && this.id == id
                        && ca.getSympathy() == CommentSympathy.DECREASE);
    }

    public CommentAccount addSympathy(CommentAccount account, long id) {
        if (checkAlreadySympathy(account, id)) {
            return account;
        }

        sympathyUsers.add(account);
        return null;
    }

    public void deleteSympathy(CommentAccount account) {
        sympathyUsers.remove(account);
    }


    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isAccount(Account account) {
        return this.account.equals(account);
    }

    public void modifyContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
