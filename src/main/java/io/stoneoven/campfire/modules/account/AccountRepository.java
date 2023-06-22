package io.stoneoven.campfire.modules.account;

import io.stoneoven.campfire.modules.tag.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findAccountByEmail(String email);

    // this is work, so do not touch anymore until entity has changed.
    @SuppressWarnings("SpringDataRepositoryMethodParametersInspection")
    Iterable<Account> findAllByTagsIn(Set<Tag> tags);

}
