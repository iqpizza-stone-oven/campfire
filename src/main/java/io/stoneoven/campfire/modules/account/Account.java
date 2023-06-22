package io.stoneoven.campfire.modules.account;

import io.stoneoven.campfire.modules.tag.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Builder @AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private RegisterType registerType;

    @Setter
    @Column(length = 40)
    private String name;

    @Setter
    private String profileImage;

    // notification when new code with interest tags
    @Builder.Default
    @Column(nullable = false)
    private boolean notificationWhenNewCode = false;

    @ManyToMany
    @Builder.Default
    private Set<Tag> tags = new HashSet<>();

    public void updateInterestTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void updateNotificationSettings(boolean notificationWhenNewCode) {
        this.notificationWhenNewCode = notificationWhenNewCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return getId() != null && Objects.equals(getId(), account.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
