package io.stoneoven.campfire.modules.main;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.account.CurrentAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(@CurrentAccount Account account, Model model) {
        if (account != null) {
            model.addAttribute(account);
        }
        return "index";
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "redirect:/login";
    }
}
