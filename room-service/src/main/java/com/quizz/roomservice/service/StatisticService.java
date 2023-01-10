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

    public LinkedHashMap getLessonById(Long lessonId) {
        ResponseObject response = webClientBuilder.build().get()
                .uri("lb://lesson-service/api/v1/lesson/lessons/" + lessonId + "/raw")
                .retrieve().bodyToMono(ResponseObject.class)
                .block();
        return (LinkedHashMap) response.getData();
    }

    public AnswerTimeStatistic getAnswerTimeStatistic(Long answerTimeId) throws Exception {
        AnswerTime answerTime = answerTimeService.getAnswerTimeById(answerTimeId);
        LinkedHashMap lessonMap = getLessonById(answerTime.getLessonId());
        return getAnswerTimeCommonStatistic(answerTime, lessonMap);
    }

    public AnswerTimeStatistic getAnswerTimeStatistic(AnswerTime answerTime) throws Exception {
        LinkedHashMap lessonMap = getLessonById(answerTime.getLessonId());
        return getAnswerTimeCommonStatistic(answerTime, lessonMap);
    }

    public AnswerTimeStatistic getAnswerTimeCommonStatistic(AnswerTime answerTime, LinkedHashMap lessonMap) throws Exception {
        AnswerTimeStatistic answerTimeStatistic = new AnswerTimeStatistic();
        BeanUtils.copyProperties(answerTime, answerTimeStatistic);
        answerTimeStatistic.setLessonTitle((String) lessonMap.get("title"));
        answerTimeStatistic.setLessonDescription((String) lessonMap.get("description"));
        List<LinkedHashMap> questions = (List<LinkedHashMap>) lessonMap.get("questions");
        Collection<QuestionAnswer> questionStatistics = new ArrayList<>();
        for (LinkedHashMap question : questions) {
            QuestionStatistic questionStatistic = new QuestionStatistic();
            QuestionAnswer foundQuestionAnswer = null;
            for (QuestionAnswer questionAnswer : answerTime.getQuestionAnswers()) {
                if (questionAnswer.getQuestionId().equals(Long.parseLong(question.get("id").toString()))) {
                    foundQuestionAnswer = questionAnswer;
                    break;
                }
            }
            if (foundQuestionAnswer != null) {
                BeanUtils.copyProperties(foundQuestionAnswer, questionStatistic);
            } else {
                questionStatistic.setId(Long.parseLong(question.get("id").toString()));
                questionStatistic.setNumberOfRightAnswer(Integer.parseInt(question.get("numberOfKeys").toString()));
            }
            List<QuestionAnswerPart> questionAnswerPartStatistics = new ArrayList<>();
            List<LinkedHashMap> answers = (List<LinkedHashMap>) question.getOrDefault("answers", new ArrayList<>());
            for (LinkedHashMap answer : answers) {
                AnswerStatistic answerStatistic = new AnswerStatistic();
                answerStatistic.setRightAnswer(false);
                if (foundQuestionAnswer != null) {
                    QuestionAnswerPart questionAnswerPart = null;
                    for (QuestionAnswerPart questionAnswerPartFind : foundQuestionAnswer.getQuestionAnswerParts()) {
                        if (questionAnswerPartFind.getAnswerId().toString().equals(answer.get("id").toString())) {
                            questionAnswerPart = questionAnswerPartFind;
                            break;
                        }
                    }
                    if (questionAnswerPart != null) {
                        BeanUtils.copyProperties(questionAnswerPart, answerStatistic);
                        answerStatistic.setSelected(true);
                    }
                }
                answerStatistic.setId(Long.parseLong(answer.get("id").toString()));
                answerStatistic.setTitle((String) answer.get("title"));
                questionAnswerPartStatistics.add(answerStatistic);
            }
            questionStatistic.setQuestionAnswerParts(questionAnswerPartStatistics);
            questionStatistic.setTitle((String) question.get("title"));
            questionStatistic.setImage((String) question.get("image"));
            questionStatistics.add(questionStatistic);
        }
        answerTimeStatistic.setQuestionAnswers(questionStatistics);
        return answerTimeStatistic;
    }

    public RoomStatistic getRoomStatisticById(Long roomId) throws Exception {
        RoomStatistic roomStatistic = new RoomStatistic();
        Room room = roomService.getRoomById(roomId);
        LinkedHashMap lessonMap = getLessonById(room.getLessonId());
        List<AnswerTime> answerTimeStatistics = new ArrayList<>();
        for (AnswerTime answerTime : room.getAnswerTimes()) {
            AnswerTimeStatistic answerStatistic = getAnswerTimeStatistic(answerTime.getId());
            answerTimeStatistics.add(answerStatistic);
        }
        BeanUtils.copyProperties(room, roomStatistic);
        roomStatistic.setLessonTitle((String) lessonMap.get("title"));
        roomStatistic.setLessonDescription((String) lessonMap.get("description"));
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

    public List<AnswerTimeStatistic> getAllAnswerTimeStatisticByUserId(String userId) throws Exception {
        List<AnswerTime> answerTimes = answerTimeService.getAllAnswerTimeByUserId(userId);
        List<AnswerTimeStatistic> answerTimeStatistics = new ArrayList<>();
        for (AnswerTime answerTime : answerTimes) {
            answerTimeStatistics.add(getAnswerTimeStatistic(answerTime));
        }
        return answerTimeStatistics;
    }

    public List<RoomStatistic> getReportList(String userId) throws Exception {
//        List<Room> rooms = roomService.findAllByUserId(userId);
        List<Room> rooms = roomService.findAll();
        List<RoomStatistic> roomStatistics = new ArrayList<>();
        for (Room room : rooms) {
            roomStatistics.add(getRoomStatisticById(room.getId()));
        }
        return roomStatistics;
    }
}
