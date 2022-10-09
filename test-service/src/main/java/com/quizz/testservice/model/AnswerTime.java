package com.quizz.testservice.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AnswerTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long lessonId;
    private Long userId;
    private LocalDateTime playedDateTime;

    @Transient
    private float accuracy;
    @Transient
    private int numberOfRight;
    @Transient
    private int numberOfWrong;
    @Transient
    private float averageDuration;
    @Transient
    private Long point;

    @OneToMany(mappedBy = "answerTime")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<QuestionAnswer> questionAnswers;
}
