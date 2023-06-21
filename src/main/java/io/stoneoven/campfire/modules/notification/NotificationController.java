package io.stoneoven.campfire.modules.notification;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.account.CurrentAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository repository;
    private final NotificationService service;

    @GetMapping("/notifications")
    public String getNotifications(@CurrentAccount Account account, Model model) {
        List<Notification> notifications = repository.findByReceiverAndCheckedOrderByCreatedAtDesc(account, false);
        long numberOfChecked = repository.countByReceiverAndChecked(account, true);
        putCategorizedNotifications(model, notifications, numberOfChecked, notifications.size());
        model.addAttribute("isNew", true);
        service.markAsRead(notifications);
        return "notification/list";
    }

    @GetMapping("/notifications/old")
    public String getOldNotifications(@CurrentAccount Account account, Model model) {
        List<Notification> notifications = repository.findByReceiverAndCheckedOrderByCreatedAtDesc(account, true);
        long numberOfNotChecked = repository.countByReceiverAndChecked(account, false);
        putCategorizedNotifications(model, notifications, notifications.size(), numberOfNotChecked);
        model.addAttribute("isNew", false);
        return "notification/list";
    }

    @DeleteMapping("/notifications")
    public String deleteNotifications(@CurrentAccount Account account) {
        repository.deleteByReceiverAndChecked(account, true);
        return "redirect:/notifications";
    }

    private void putCategorizedNotifications(Model model, List<Notification> notifications,
                                             long numberOfChecked, long numberOfNotChecked) {
        List<Notification> systemNotifications = new ArrayList<>();
        List<Notification> reviewReceiveNotifications = new ArrayList<>();
        List<Notification> newReviewNotifications = new ArrayList<>();
        for (var notification : notifications) {
            switch (notification.getNotificationType()) {
                case SYSTEM -> systemNotifications.add(notification);
                case REVIEW_RECEIVE -> reviewReceiveNotifications.add(notification);
                case REVIEW_REQUEST -> newReviewNotifications.add(notification);
            }
        }

        model.addAttribute("numberOfNotChecked", numberOfNotChecked);
        model.addAttribute("numberOfChecked", numberOfChecked);
        model.addAttribute("notifications", notifications);
        model.addAttribute("systemNotifications", systemNotifications);
        model.addAttribute("reviewReceiveNotifications", reviewReceiveNotifications);
        model.addAttribute("newReviewNotifications", newReviewNotifications);
    }
}
