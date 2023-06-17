package io.stoneoven.campfire.modules.main;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.account.CurrentAccount;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    public String handleRuntimeException(@CurrentAccount Account account,
                                         HttpServletRequest request,
                                         RuntimeException e) {
        if (account != null) {
            log.info("`{}` requested `{}`", account.getName(), request.getRequestURI());
        }
        else {
            log.info("requested `{}`", request.getRequestURI());
        }
        log.error("bad request", e);
        return "error";
    }
}
