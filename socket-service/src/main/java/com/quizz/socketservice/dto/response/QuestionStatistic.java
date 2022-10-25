package com.quizz.socketservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionStatistic {
    private int numberOfAnswers;
    private int point;
    private int duration;
    private Collection<String> rank;
    private int currentRank;
}
