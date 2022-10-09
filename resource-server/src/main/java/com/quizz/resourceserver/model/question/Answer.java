package com.quizz.resourceserver.model.question;

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
    private String title;
    private boolean key;
    private int disOrder;
    private boolean disFlg;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Question question;
}
