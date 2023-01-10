package com.quizz.roomservice.service;

import com.quizz.roomservice.event.AnswerTimeAddedEvent;
import com.quizz.roomservice.model.AnswerTime;
import com.quizz.roomservice.model.QuestionAnswer;
import com.quizz.roomservice.model.QuestionAnswerPart;
import com.quizz.roomservice.repository.AnswerTimeRepository;
import com.quizz.roomservice.repository.AnswerTimeRepositoryCustomImpl;
import com.quizz.roomservice.repository.QuestionAnswerPartRepository;
import com.quizz.roomservice.repository.QuestionAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerTimeService {

    private final AnswerTimeRepository answerTimeRepository;
    private final AnswerTimeRepositoryCustomImpl answerTimeCustom;
    private final QuestionAnswerRepository questionAnswerRepository;
    private final QuestionAnswerPartRepository questionAnswerPartRepository;
    private final KafkaTemplate<String, AnswerTimeAddedEvent> kafkaTemplate;

    public Long getNumberOfAnswerTime(Long lessonId) {
        return answerTimeCustom.countAnswerTime(lessonId);
    }

    public AnswerTime getAnswerTimeById(Long id) throws Exception {
        Optional<AnswerTime> answerTimeOptional = answerTimeRepository.findById(id);
        if (!answerTimeOptional.isPresent()) throw new Exception("Answer time not found");
        return answerTimeOptional.get();
    }

    public List<AnswerTime> getByLessonId(Long lessonId) throws Exception {
        return answerTimeRepository.getByLessonId(lessonId);
    }

    public AnswerTime addAnswerTime(AnswerTime answerTime) {
        AnswerTime savedAnswerTime = answerTimeRepository.save(answerTime);
        if (savedAnswerTime.getQuestionAnswers() != null) {
            for (QuestionAnswer questionAnswer : savedAnswerTime.getQuestionAnswers()) {
                questionAnswer.setAnswerTime(savedAnswerTime);
            }
        }
        List<QuestionAnswer> questionAnswers = questionAnswerRepository.saveAll(savedAnswerTime.getQuestionAnswers().stream().toList());
        for (QuestionAnswer questionAnswer : questionAnswers) {
            for (QuestionAnswerPart questionAnswerPart : questionAnswer.getQuestionAnswerParts()) {
                questionAnswerPart.setQuestionAnswer(questionAnswer);
            }
            questionAnswerPartRepository.saveAll(questionAnswer.getQuestionAnswerParts());
        }
        AnswerTimeAddedEvent answerTimeAddedEvent = new AnswerTimeAddedEvent();
        answerTimeAddedEvent.setCreatedTime(LocalDateTime.now());
        answerTimeAddedEvent.setLessonId(answerTime.getLessonId());
        if (answerTime.getRoom() != null && answerTime.getRoom().getId() != null) {
            answerTimeAddedEvent.setRoomId(answerTime.getRoom().getId());
        }
        answerTimeAddedEvent.setAnswerTimeId(savedAnswerTime.getId());
        answerTimeAddedEvent.setUserId(savedAnswerTime.getUserId());
        kafkaTemplate.send("notificationTopic", answerTimeAddedEvent);
        return answerTimeRepository.findById(savedAnswerTime.getId()).get();
    }

    public List<AnswerTime> getAllAnswerTimeByUserId(String userId) {
        return answerTimeRepository.findAllByUserId(userId);
    }
}
