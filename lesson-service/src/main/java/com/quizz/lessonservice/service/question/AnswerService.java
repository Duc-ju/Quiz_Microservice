package com.quizz.lessonservice.service.question;

import com.quizz.lessonservice.model.question.Answer;
import com.quizz.lessonservice.repository.question.AnswerRepository;
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
