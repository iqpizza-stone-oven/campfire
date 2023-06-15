package io.stoneoven.campfire.modules.account;

import io.stoneoven.campfire.modules.tag.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
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

    @Builder.Default
    @Column(nullable = false)
    private boolean emailVerify = false;

    @Column(length = 8)
    private String emailVerifyToken;

    private LocalDateTime emailTokenGeneratedAt;

    @Column(length = 40)
    private String name;

    private String profileImage;

    @Builder.Default
    @Column(nullable = false)
    private short coin = 5;

    @ManyToMany
    @Builder.Default
    private Set<Tag> tags = new HashSet<>();

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
