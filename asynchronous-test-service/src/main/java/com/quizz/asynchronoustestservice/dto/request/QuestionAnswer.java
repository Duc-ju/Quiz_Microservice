package com.quizz.asynchronoustestservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswer {
    private Long id;
    private Long questionId;
    private Collection<QuestionAnswerPart> questionAnswerParts;
    private AnswerTime answerTime;
}
