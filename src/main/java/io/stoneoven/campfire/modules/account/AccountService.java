package io.stoneoven.campfire.modules.account;

import io.stoneoven.campfire.modules.account.form.ProfileForm;
import io.stoneoven.campfire.modules.account.form.SettingForm;
import io.stoneoven.campfire.modules.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TagService tagService;

    public Account getAccount(long id) {
        return accountRepository.findById(id)
                .orElseThrow();
    }

    public void updateProfile(Account account, ProfileForm profileForm) {
        account.setName(profileForm.getName());
        account.updateInterestTags(tagService.convertToTags(profileForm.getTags()));
        accountRepository.save(account);
    }

    public void updateSetting(SettingForm settingForm) {
        Account account = getAccount(settingForm.getId());
        account.updateNotificationSettings(settingForm.isNewCode(), settingForm.isCoin());
    }
}
