package com.quizz.lessonservice.model.question;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.quizz.lessonservice.model.lesson.Lesson;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 2048)
    private String title;

    @Column(length = 1024)
    private String image;
    private int duration;
    private int point;
    private int numberOfKeys;

    @OneToMany(mappedBy = "question")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private Collection<Answer> answers;

    private boolean disFlg;
    private boolean overlooked;
    private Date updatedAt;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "lesson_id")
    @JsonBackReference
    private Lesson lesson;

}
