package com.quizz.roomservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswerTimeStatistic extends AnswerTime {
    private UserInfo userInfo;
    private String lessonTitle;
    private String lessonDescription;
}
