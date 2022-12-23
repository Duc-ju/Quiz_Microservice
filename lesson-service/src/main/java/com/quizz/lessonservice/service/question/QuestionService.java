package com.quizz.lessonservice.service.question;

import com.quizz.lessonservice.model.question.Answer;
import com.quizz.lessonservice.model.question.Question;
import com.quizz.lessonservice.repository.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerService answerService;

    /**
     * This method add new Question to DB
     *
     * @param question
     * @return added question
     * @throws Exception
     */
    public Question addQuestion(Question question) {
        log.info("Add question {}", question);
        Question savedQuestion = questionRepository.save(question);
        for (Answer answer : question.getAnswers()) {
            answer.setQuestion(savedQuestion);
        }
        List<Answer> answers = answerService.addAnswers(question.getAnswers().stream().toList());
        savedQuestion.setAnswers(answers);
        return savedQuestion;
    }

    public List<Question> addQuestions(List<Question> questions) {
        List<Question> res = new ArrayList<>();
        for (Question question : questions) {
            Question savedQuestion = addQuestion(question);
            res.add(savedQuestion);
        }
        return res;
    }

    /**
     * This method update question information
     *
     * @param newQuestion, id
     * @return added question
     * @throws Exception
     */
    public Question updateQuestion(Question newQuestion, Long id) throws Exception {
        Optional<Question> questionOptional = questionRepository.findById(id).map(question -> {
            BeanUtils.copyProperties(newQuestion, question);
            question.setUpdatedAt(Calendar.getInstance().getTime());
            return questionRepository.save(question);
        });
        if (!questionOptional.isPresent()) {
            throw new Exception("Can not find question with id " + id);
        }
        return questionOptional.get();
    }

    public Question getQuestion(Long id) throws Exception {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (!optionalQuestion.isPresent()) {
            throw new Exception("Can not find question with id " + id);
        }
        return optionalQuestion.get();
    }

    public List<Question> getQuestionsByLessonId(Long lessonId) {
        return questionRepository.findByLessonId(lessonId);
    }

    public int getNumberOfKeysByQuestionId(Long questionId) {
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if (questionOptional.isPresent()) return questionOptional.get().getNumberOfKeys();
        return -1;
    }
}
