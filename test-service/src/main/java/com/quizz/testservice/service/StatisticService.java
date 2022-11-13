package com.quizz.testservice.service;

import com.quizz.testservice.dto.QuestionStatistic;
import com.quizz.testservice.model.QuestionAnswer;
import com.quizz.testservice.repository.StatisticRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final StatisticRepositoryImpl statisticRepository;

    public QuestionStatistic getQuestionStatistic(QuestionAnswer questionAnswer) {
        return statisticRepository.getQuestionStatistic(questionAnswer);
    }
}
