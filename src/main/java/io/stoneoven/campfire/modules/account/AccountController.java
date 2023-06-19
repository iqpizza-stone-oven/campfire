package io.stoneoven.campfire.modules.account;

import io.stoneoven.campfire.modules.account.form.ProfileForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/login")
    public String loginForm() {
        return "account/login";
    }

    @GetMapping("/profile/{name}")
    public String profileForm(@CurrentAccount Account account, Model model,
                              @PathVariable String name) {
        Account fetchedAccount = accountService.getAccount(account.getId());
        final String currentName = fetchedAccount.getName();
        if (!currentName.equals(name) && !currentName.equals(URLDecoder.decode(name, StandardCharsets.UTF_8))) {
            throw new IllegalArgumentException("Current account name isn't correct");
        }

        model.addAttribute(fetchedAccount);
        model.addAttribute(new ProfileForm(fetchedAccount));
        return "account/profile";
    }

    @PutMapping("/profile")
    public String modifyProfile(@CurrentAccount Account account,
                                ProfileForm profileForm) {
        Account fetchedAccount = accountService.getAccount(account.getId());
        accountService.updateProfile(fetchedAccount, profileForm);
        final String currentName = fetchedAccount.getName();
        account.setName(currentName);
        return "redirect:/profile/" + URLEncoder.encode(currentName, StandardCharsets.UTF_8);
    }
}
