package com.quizz.roomservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private Long questionId;

    @OneToMany(mappedBy = "questionAnswer")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private Collection<QuestionAnswerPart> questionAnswerParts;

    @Transient
    private boolean rightAnswer;

    private int numberOfRightAnswer;

    @Getter(AccessLevel.NONE)
    @Transient
    private Long numberOfAnswerPart;

    private long point;

    private int duration;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "answer_time_id")
    @JsonBackReference
    private AnswerTime answerTime;

    public Long getNumberOfAnswerPart() {
        if (questionAnswerParts == null) return (long) -1;
        return (long) questionAnswerParts.size();
    }

    public boolean isRightAnswer() {
        if (questionAnswerParts == null) return false;
        return numberOfRightAnswer <= questionAnswerParts.stream().filter(QuestionAnswerPart::isRightAnswer).toList().size();
    }
}
