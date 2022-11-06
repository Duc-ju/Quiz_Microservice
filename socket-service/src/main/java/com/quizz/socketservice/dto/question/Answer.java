package com.quizz.socketservice.dto.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    private Long id;
    private String title;
    private boolean answerKey;
    private int disOrder;
    private boolean disFlg;
}
