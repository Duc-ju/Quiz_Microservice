package com.quizz.roomservice.repository;

import com.quizz.roomservice.model.QuestionAnswerPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionAnswerPartRepository extends JpaRepository<QuestionAnswerPart, Long> {
    List<QuestionAnswerPart> getQuestionAnswerPartByQuestionAnswerId(Long id);
}
