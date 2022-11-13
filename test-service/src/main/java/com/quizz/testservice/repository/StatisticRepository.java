package com.quizz.testservice.repository;

import com.quizz.testservice.dto.QuestionStatistic;
import com.quizz.testservice.model.QuestionAnswer;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository {
    QuestionStatistic getQuestionStatistic(QuestionAnswer questionAnswer);

    int getNumberOfRightAnswer(QuestionAnswer questionAnswer);
}
