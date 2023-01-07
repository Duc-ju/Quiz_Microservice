package com.quizz.roomservice.service;

import com.quizz.roomservice.common.ResponseObject;
import com.quizz.roomservice.model.*;
import com.quizz.roomservice.repository.StatisticRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class StatisticService {

    private final StatisticRepositoryImpl statisticRepository;
    private final AnswerTimeService answerTimeService;
    private final WebClient.Builder webClientBuilder;

    private final RoomService roomService;

    public AnswerTimeStatistic getAnswerTimeStatistic(Long answerTimeId) throws Exception {
        AnswerTime answerTime = answerTimeService.getAnswerTimeById(answerTimeId);
        ResponseObject response = webClientBuilder.build().get()
                .uri("lb://lesson-service/api/v1/lesson/lessons/" + answerTime.getLessonId())
                .retrieve().bodyToMono(ResponseObject.class)
                .block();
        AnswerTimeStatistic answerTimeStatistic = new AnswerTimeStatistic();
        BeanUtils.copyProperties(answerTime, answerTimeStatistic);
        LinkedHashMap lessonMap = (LinkedHashMap) response.getData();
        answerTimeStatistic.setLessonTitle((String) lessonMap.get("title"));
        answerTimeStatistic.setLessonDescription((String) lessonMap.get("description"));
        List<LinkedHashMap> questions = (List<LinkedHashMap>) lessonMap.get("questions");
        Collection<QuestionAnswer> questionStatistics = new ArrayList<>();
        for (QuestionAnswer questionAnswer : answerTimeStatistic.getQuestionAnswers()) {
            QuestionStatistic questionStatistic = new QuestionStatistic();
            BeanUtils.copyProperties(questionAnswer, questionStatistic);
            LinkedHashMap currentQuestion = null;
            for (LinkedHashMap questionMap : questions) {
                if (questionMap.get("id").toString().equals(questionAnswer.getQuestionId().toString())) {
                    currentQuestion = questionMap;
                    break;
                }
            }
            if (currentQuestion != null) {
                questionStatistic.setTitle((String) currentQuestion.get("title"));
                questionStatistic.setImage((String) currentQuestion.get("image"));
                List<QuestionAnswerPart> questionAnswerPartStatistics = new ArrayList<>();
                List<LinkedHashMap> answers = (List<LinkedHashMap>) currentQuestion.getOrDefault("answers", new ArrayList<>());
                for (LinkedHashMap answer : answers) {
                    AnswerStatistic answerStatistic = new AnswerStatistic();
                    QuestionAnswerPart questionAnswerPart = null;
                    for (QuestionAnswerPart questionAnswerPartFind : questionAnswer.getQuestionAnswerParts()) {
                        if (questionAnswerPartFind.getAnswerId().toString().equals(answer.get("id").toString())) {
                            questionAnswerPart = questionAnswerPartFind;
                            break;
                        }
                    }
                    if (questionAnswerPart != null) {
                        BeanUtils.copyProperties(questionAnswerPart, answerStatistic);
                        answerStatistic.setSelected(true);
                    }
                    answerStatistic.setId(Long.parseLong(answer.get("id").toString()));
                    answerStatistic.setTitle((String) answer.get("title"));
                    questionAnswerPartStatistics.add(answerStatistic);
                }
                questionStatistic.setQuestionAnswerParts(questionAnswerPartStatistics);
            }
            questionStatistics.add(questionStatistic);

        }
        answerTimeStatistic.setQuestionAnswers(questionStatistics);
        return answerTimeStatistic;
    }

    public RoomStatistic getRoomStatisticById(Long roomId) throws Exception {
        RoomStatistic roomStatistic = new RoomStatistic();
        Room room = roomService.getRoomById(roomId);
        List<AnswerTime> answerTimeStatistics = new ArrayList<>();
        for (AnswerTime answerTime : room.getAnswerTimes()) {
            AnswerTimeStatistic answerStatistic = getAnswerTimeStatistic(answerTime.getId());
            answerTimeStatistics.add(answerStatistic);
        }
        BeanUtils.copyProperties(room, roomStatistic);
        roomStatistic.setAnswerTimes(answerTimeStatistics);
        return roomStatistic;
    }

    public RoomStatistic getRoomStatisticByLessonId(Long lessonId) throws Exception {
        RoomStatistic roomStatistic = new RoomStatistic();
        List<AnswerTime> answerTimes = answerTimeService.getByLessonId(lessonId);
        List<AnswerTime> answerTimeStatistics = new ArrayList<>();
        for (AnswerTime answerTime : answerTimes) {
            AnswerTimeStatistic answerStatistic = getAnswerTimeStatistic(answerTime.getId());
            answerTimeStatistics.add(answerStatistic);
        }
        roomStatistic.setAnswerTimes(answerTimeStatistics);
        return roomStatistic;
    }

    public ChartStatistic getAfterQuestionChart(Long answerTimeId, Long questionId) throws Exception {
//        AnswerTime answerTime = answerTimeService.getAnswerTimeById(answerTimeId);
//        RoomStatistic roomStatistic = null;
//        if (answerTime.getRoom() != null) {
//            roomStatistic = getRoomStatisticById(answerTime.getRoom().getId());
//        } else {
//            roomStatistic = getRoomStatisticByLessonId(answerTime.getLessonId());
//        }
//        return roomStatistic.getChartStatistic(answerTime.getUserId(), questionId);
        ChartStatistic chartStatistic = new ChartStatistic();
        List<UserRankStatistic> userRankStatistics = new ArrayList<>();
        UserRankStatistic userRankStatistic = new UserRankStatistic();
        userRankStatistic.setRank(1);
        userRankStatistic.setAvatar("");
        userRankStatistic.setUsername("Đức đẹp zai");
        userRankStatistic.setId("1");
        userRankStatistics.add(userRankStatistic);
        userRankStatistic.setRank(2);
        userRankStatistic.setAvatar("");
        userRankStatistic.setUsername("Hoa Tuyết");
        userRankStatistic.setId("2");
        userRankStatistics.add(userRankStatistic);
        userRankStatistic.setRank(3);
        userRankStatistic.setAvatar("");
        userRankStatistic.setUsername("Ly chân dài");
        userRankStatistic.setId("3");
        userRankStatistics.add(userRankStatistic);
        chartStatistic.setUserRankStatistics(userRankStatistics);
        chartStatistic.setCurrentRank(1);
        return chartStatistic;
    }
}
