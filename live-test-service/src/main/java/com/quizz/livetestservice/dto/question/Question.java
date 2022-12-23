package com.quizz.livetestservice.dto.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    private Long id;
    private String title;
    private String image;
    private int duration;
    private int point;
    private int numberOfKeys;
    private Collection<Answer> answers;
    private boolean disFlg;
    private boolean overlooked;
    private Date updatedAt;

}
