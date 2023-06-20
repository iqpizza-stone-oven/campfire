package io.stoneoven.campfire.modules.account;

import io.stoneoven.campfire.modules.account.form.SettingForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SettingController {

    private final AccountService accountService;

    @PutMapping("/settings/notification")
    public void updateNotificationSetting(@RequestBody SettingForm settingForm) {
        accountService.updateSetting(settingForm);
    }

}
