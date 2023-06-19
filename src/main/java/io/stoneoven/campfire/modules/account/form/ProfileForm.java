package io.stoneoven.campfire.modules.account.form;

import io.stoneoven.campfire.modules.account.Account;
import io.stoneoven.campfire.modules.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ProfileForm {

    private String name;

    private String tags;

    public ProfileForm(Account account) {
        this.name = account.getName();
        this.tags = account.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.joining(","));
    }
}
