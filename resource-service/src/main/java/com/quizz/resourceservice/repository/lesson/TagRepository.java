package com.quizz.resourceservice.repository.lesson;

import com.quizz.resourceservice.model.lesson.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
