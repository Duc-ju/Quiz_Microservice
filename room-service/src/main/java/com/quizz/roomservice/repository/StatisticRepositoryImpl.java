package com.quizz.roomservice.repository;

import com.quizz.roomservice.common.ResponseObject;
import com.quizz.roomservice.dto.QuestionStatistic;
import com.quizz.roomservice.model.QuestionAnswer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Log4j2
public class StatisticRepositoryImpl implements StatisticRepository {

    private final WebClient.Builder webClientBuilder;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public QuestionStatistic getQuestionStatistic(QuestionAnswer questionAnswer) {
        ResponseObject numberOfKeysResponse = webClientBuilder.build().get()
                .uri("lb://lesson-service/api/v1/resource/questions/" + questionAnswer.getQuestionId() + "/numberOfKeys")
                .retrieve().bodyToMono(ResponseObject.class)
                .block();
        int numberOfRightAnswer = Integer.valueOf(numberOfKeysResponse.getData().toString()).intValue();
        int numberOfUserRightAnswer = getNumberOfRightAnswer(questionAnswer);
        QuestionStatistic questionStatistic = new QuestionStatistic();
        questionStatistic.setRight(numberOfRightAnswer <= numberOfUserRightAnswer);
        return questionStatistic;
    }

    @Override
    public int getNumberOfRightAnswer(QuestionAnswer questionAnswer) {
        final String sql = "SELECT COUNT(1) FROM QUESTION_ANSWER_PART, QUESTION_ANSWER WHERE QUESTION_ANSWER_PART.QUESTION_ANSWER_ID = QUESTION_ANSWER.ID AND QUESTION_ANSWER.ID = ? AND QUESTION_ANSWER_PART.RIGHT_ANSWER = TRUE";
        log.info(sql);
        return jdbcTemplate.queryForObject(sql, Integer.class, questionAnswer.getId());
    }
}
