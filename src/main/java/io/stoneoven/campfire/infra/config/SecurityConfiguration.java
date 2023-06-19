package io.stoneoven.campfire.infra.config;

import io.stoneoven.campfire.modules.account.AccountOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AccountOAuthService accountOAuthService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request ->
                request.requestMatchers(
                        "/", "/login", "/sign-up",
                        "/check-email-token",
                        "/review-page",
                        "/search/review"
                ).permitAll()
                .anyRequest().authenticated());
        //noinspection removal
        http.oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login")
                .userInfoEndpoint()
                .userService(accountOAuthService);
        return
                http.build();
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers("/node_modules/**")
                .requestMatchers("/favicon.ico", "/resources/**", "/error")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
