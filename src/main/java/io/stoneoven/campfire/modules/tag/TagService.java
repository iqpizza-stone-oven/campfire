package io.stoneoven.campfire.modules.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public Tag findOrCreateNew(String tagTitle) {
        Tag tag = tagRepository.findByName(tagTitle);
        if (tag == null) {
            tag = tagRepository.save(new Tag(null, tagTitle));
        }

        return tag;
    }

    public Set<Tag> convertToTags(String rawTags) {
        List<String> tags = Arrays.stream(rawTags.strip().split(",")).toList();
        return tags.stream().map(this::findOrCreateNew)
                .collect(Collectors.toSet());
    }
}
