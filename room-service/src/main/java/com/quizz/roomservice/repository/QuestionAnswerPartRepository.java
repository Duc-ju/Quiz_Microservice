package com.quizz.roomservice.repository;

import com.quizz.roomservice.model.QuestionAnswerPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionAnswerPartRepository extends JpaRepository<QuestionAnswerPart, Long> {
}
