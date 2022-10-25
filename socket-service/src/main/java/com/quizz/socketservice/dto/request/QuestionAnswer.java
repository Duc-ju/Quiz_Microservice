package com.quizz.socketservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswer {
    private Long questionId;
    private Long answerTimeId;
    Collection<QuestionAnswerPart> questionAnswerParts;
}
