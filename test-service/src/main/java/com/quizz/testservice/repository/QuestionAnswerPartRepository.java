package com.quizz.testservice.repository;

import com.quizz.testservice.model.QuestionAnswerPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionAnswerPartRepository extends JpaRepository<QuestionAnswerPart, Long> {
}
