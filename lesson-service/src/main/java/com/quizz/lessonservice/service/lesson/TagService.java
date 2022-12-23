package com.quizz.lessonservice.service.lesson;

import com.quizz.lessonservice.model.lesson.Tag;
import com.quizz.lessonservice.repository.lesson.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> getAllTag() {
        return tagRepository.findAll();
    }

    public Tag addTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public List<Tag> addTags(List<Tag> tags) {
        return tagRepository.saveAll(tags);
    }
}
