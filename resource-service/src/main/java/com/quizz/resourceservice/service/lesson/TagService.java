package com.quizz.resourceservice.service.lesson;

import com.quizz.resourceservice.model.lesson.Tag;
import com.quizz.resourceservice.repository.lesson.TagRepository;
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
}
