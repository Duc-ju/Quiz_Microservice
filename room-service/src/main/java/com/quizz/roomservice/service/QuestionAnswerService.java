package com.quizz.roomservice.service;

import com.quizz.roomservice.model.QuestionAnswer;
import com.quizz.roomservice.model.QuestionAnswerPart;
import com.quizz.roomservice.repository.QuestionAnswerPartRepository;
import com.quizz.roomservice.repository.QuestionAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class QuestionAnswerService {

    private final QuestionAnswerRepository questionAnswerRepository;

    private final QuestionAnswerPartRepository questionAnswerPartRepository;

    public QuestionAnswer addQuestionAnswer(QuestionAnswer questionAnswer) {
        QuestionAnswer questionAnswerSaved = questionAnswerRepository.save(questionAnswer);
        Collection<QuestionAnswerPart> questionAnswerParts = questionAnswer.getQuestionAnswerParts();
        for (QuestionAnswerPart questionAnswerPart : questionAnswerParts) {
            questionAnswerPart.setQuestionAnswer(questionAnswerSaved);
        }
        Collection<QuestionAnswerPart> questionAnswerPartsSaved = questionAnswerPartRepository.saveAll(questionAnswerParts);
        questionAnswerSaved.setQuestionAnswerParts(questionAnswerPartsSaved);
        return questionAnswerSaved;
    }
}
