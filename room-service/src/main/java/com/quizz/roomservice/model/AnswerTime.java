package com.quizz.roomservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class AnswerTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long lessonId;
    private String userId;
    private LocalDateTime playedDateTime;

    @Getter(AccessLevel.NONE)
    @Transient
    private double accuracy;

    @Getter(AccessLevel.NONE)
    @Transient
    private int numberOfRight;

    @Getter(AccessLevel.NONE)
    @Transient
    private int numberOfWrong;

    @Getter(AccessLevel.NONE)
    @Transient
    private double averageDuration;

    @Getter(AccessLevel.NONE)
    @Transient
    private long point;

    private String nickName;

    @OneToMany(mappedBy = "answerTime")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private Collection<QuestionAnswer> questionAnswers;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private Room room;

    public int getNumberOfRight() {
        if (questionAnswers == null) return -1;
        int count = 0;
        for (QuestionAnswer questionAnswer : questionAnswers) {
            if (questionAnswer.isRightAnswer()) count++;
        }
        return count;
    }

    public int getNumberOfWrong() {
        if (questionAnswers == null) return -1;
        int count = 0;
        for (QuestionAnswer questionAnswer : questionAnswers) {
            if (!questionAnswer.isRightAnswer()) count++;
        }
        return count;
    }

    public double getAccuracy() {
        if (questionAnswers == null || questionAnswers.isEmpty()) return 0;
        return getNumberOfRight() * 1.0 / questionAnswers.size();
    }

    public Long getPoint() {
        if (questionAnswers == null) return (long) 0;
        long point = 0;
        for (QuestionAnswer questionAnswer : questionAnswers) {
            point += questionAnswer.getPoint();
        }
        return point;
    }

    public Long getPoint(Long questionId) {
        if (questionAnswers == null) return (long) -1;
        long point = 0;
        for (QuestionAnswer questionAnswer : questionAnswers) {
            point += questionAnswer.getPoint();
            if (questionId.equals(questionAnswer.getQuestionId())) break;
        }
        return point;
    }

    public double getAverageDuration() {
        if (questionAnswers == null) return (long) -1;
        long sumDuration = 0;
        for (QuestionAnswer questionAnswer : questionAnswers) {
            sumDuration += questionAnswer.getDuration();
        }
        return sumDuration * (1.0) / questionAnswers.size();
    }
}
