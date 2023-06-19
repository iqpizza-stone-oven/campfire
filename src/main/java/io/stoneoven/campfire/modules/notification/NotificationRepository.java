package io.stoneoven.campfire.modules.notification;

import io.stoneoven.campfire.modules.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    long countByReceiverAndChecked(Account receiver, boolean checked);

    List<Notification> findByReceiverAndCheckedOrderByCreatedAtDesc(Account receiver, boolean checked);

    void deleteByReceiverAndChecked(Account receiver, boolean checked);

}
