package com.quizz.livetestservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionStatistic {
    private boolean right;
    private int point;
//    private int numberOfAnswers;
//    private int duration;
//    private Collection<String> rank;
//    private int currentRank;
}
