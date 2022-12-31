package com.quizz.roomservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionStatistic extends QuestionAnswer {
    private String title;
    private String image;
    private int averageDuration;
    private int numberOfRight;
    private int numberOfWrong;
}
