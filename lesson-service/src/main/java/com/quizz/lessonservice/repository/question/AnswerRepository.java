package com.quizz.lessonservice.repository.question;

import com.quizz.lessonservice.model.question.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
