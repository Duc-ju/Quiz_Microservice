package com.quizz.resourceservice.service.question;

import com.quizz.resourceservice.model.question.Answer;
import com.quizz.resourceservice.repository.question.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public List<Answer> addAnswers(List<Answer> answers) {
        return answerRepository.saveAll(answers);
    }
}
