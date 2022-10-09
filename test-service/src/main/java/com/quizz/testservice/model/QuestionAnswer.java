package com.quizz.testservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class QuestionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;
    private Long questionId;

    @OneToMany(mappedBy="questionAnswer")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<QuestionAnswerPart> questionAnswerParts;

    @Transient
    private boolean right;

    private int duration;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "answer_time_id")
    private AnswerTime answerTime;
}
