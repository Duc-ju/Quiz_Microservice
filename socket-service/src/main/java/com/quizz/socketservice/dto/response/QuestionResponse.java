package com.quizz.socketservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {
    private Long id;
    private String title;
    private String image;
    private int duration;
    private int point;
    private int numberOfKeys;
    private Collection<Answer> answers;
}
