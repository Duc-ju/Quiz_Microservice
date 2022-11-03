package com.quizz.resourceservice.repository.question;

import com.quizz.resourceservice.model.question.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
