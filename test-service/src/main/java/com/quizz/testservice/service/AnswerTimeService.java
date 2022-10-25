package com.quizz.testservice.service;

import com.quizz.testservice.model.AnswerTime;
import com.quizz.testservice.repository.AnswerTimeRepository;
import com.quizz.testservice.repository.AnswerTimeRepositoryCustomImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerTimeService {

    private final AnswerTimeRepository answerTimeRepository;
    private final AnswerTimeRepositoryCustomImpl answerTimeCustom;

    public Long getAnswerTime(Long lessonId) {
        return answerTimeCustom.countAnswerTime(lessonId);
    }

    public AnswerTime addAnswerTime(AnswerTime answerTime) {
        return answerTimeRepository.save(answerTime);
    }

    public void deleteAnswerTimeBySocketId(String socketId) {
        answerTimeRepository.deleteAnswerTimeBySocketId(socketId);
    }
}
