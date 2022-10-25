package com.quizz.socketservice.dto.response;

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
}
