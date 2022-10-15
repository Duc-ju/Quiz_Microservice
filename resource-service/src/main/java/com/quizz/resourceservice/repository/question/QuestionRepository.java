package com.quizz.resourceservice.repository.question;

import com.quizz.resourceservice.model.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
