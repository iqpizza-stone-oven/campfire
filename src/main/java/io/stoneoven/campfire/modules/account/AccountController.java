package io.stoneoven.campfire.modules.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {

    @GetMapping("/login")
    public String loginForm() {
        return "account/login";
    }
}
