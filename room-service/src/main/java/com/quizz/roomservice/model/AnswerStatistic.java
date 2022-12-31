package com.quizz.roomservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerStatistic extends QuestionAnswerPart {
    private String title;
    private boolean selected;
    private int numberOfSelected;
}
