package com.quizz.synchronoustestservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswerPart {
    private Long id;
    private Long answerId;
    private boolean rightAnswer;
}
