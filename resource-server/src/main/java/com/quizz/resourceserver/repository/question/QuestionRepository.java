package com.quizz.resourceserver.repository.question;

import com.quizz.resourceserver.model.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
