package com.quizz.roomservice.service;

import com.quizz.roomservice.model.AnswerTime;
import com.quizz.roomservice.model.QuestionAnswer;
import com.quizz.roomservice.model.QuestionAnswerPart;
import com.quizz.roomservice.repository.AnswerTimeRepository;
import com.quizz.roomservice.repository.AnswerTimeRepositoryCustomImpl;
import com.quizz.roomservice.repository.QuestionAnswerPartRepository;
import com.quizz.roomservice.repository.QuestionAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerTimeService {

    private final AnswerTimeRepository answerTimeRepository;
    private final AnswerTimeRepositoryCustomImpl answerTimeCustom;

    private final QuestionAnswerService questionAnswerService;

    private final QuestionAnswerRepository questionAnswerRepository;

    private final QuestionAnswerPartRepository questionAnswerPartRepository;

    public Long getNumberOfAnswerTime(Long lessonId) {
        return answerTimeCustom.countAnswerTime(lessonId);
    }

    public AnswerTime getAnswerTime(Long id) throws Exception {
        Optional<AnswerTime> answerTimeOptional = answerTimeRepository.findById(id);
        if (!answerTimeOptional.isPresent()) throw new Exception("Answer time not found");
        return answerTimeOptional.get();
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
        return answerTimeRepository.findById(savedAnswerTime.getId()).get();
    }

    public void deleteAnswerTimeBySocketId(String socketId) {
        answerTimeRepository.deleteAnswerTimeBySocketId(socketId);
    }
}