package io.stoneoven.campfire.modules.comment;

import io.stoneoven.campfire.modules.account.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CARepository extends CrudRepository<CommentAccount, Long> {

    Optional<CommentAccount> findByAccountAndComment(Account account, Comment comment);

}
