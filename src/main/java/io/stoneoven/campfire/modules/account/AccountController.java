package io.stoneoven.campfire.modules.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class AccountController {

    @GetMapping("/login")
    public String loginForm() {
        return "account/login";
    }

    @GetMapping("/profile/{name}")
    public String profileForm(@CurrentAccount Account account, Model model,
                              @PathVariable String name) {
        if (!account.getName().equals(name)) {
            // error occurs
            throw new IllegalArgumentException("Current account name isn't correct");
        }

        model.addAttribute(account);
        return "account/profile";
    }

}
