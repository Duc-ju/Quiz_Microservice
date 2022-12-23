package com.quizz.roomservice.repository;

import com.quizz.roomservice.dto.QuestionStatistic;
import com.quizz.roomservice.model.QuestionAnswer;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository {
    QuestionStatistic getQuestionStatistic(QuestionAnswer questionAnswer);

    int getNumberOfRightAnswer(QuestionAnswer questionAnswer);
}
