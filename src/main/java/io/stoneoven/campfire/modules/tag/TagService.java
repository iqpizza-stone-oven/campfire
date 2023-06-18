package io.stoneoven.campfire.modules.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
