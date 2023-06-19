package io.stoneoven.campfire.modules.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountOAuthService extends DefaultOAuth2UserService {

    private final AccountRepository accountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String profile = oAuth2User.getAttribute("picture");

        Account account = accountRepository.findAccountByEmail(email)
                .orElse(null);
        if (account == null) {
            account = accountRepository.save(Account.builder()
                    .email(email)
                    .name(name)
                    .profileImage(profile)
                    .registerType(RegisterType.valueOf(provider.toUpperCase()))
                    .build());
        }

        return new UserAccount(account, oAuth2User.getAttributes());
    }
}
