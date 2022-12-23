package com.quizz.lessonservice.model.question;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 2048)
    private String title;
    private boolean answerKey;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private int disOrder;
    private boolean disFlg;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private Question question;
}
