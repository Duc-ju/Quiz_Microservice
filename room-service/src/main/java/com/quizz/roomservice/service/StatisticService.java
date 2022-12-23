package com.quizz.roomservice.service;

import com.quizz.roomservice.dto.QuestionStatistic;
import com.quizz.roomservice.model.QuestionAnswer;
import com.quizz.roomservice.repository.StatisticRepositoryImpl;
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
