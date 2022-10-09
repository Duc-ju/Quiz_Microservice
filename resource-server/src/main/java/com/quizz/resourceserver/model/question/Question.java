package com.quizz.resourceserver.model.question;

import com.quizz.resourceserver.model.lesson.Category;
import com.quizz.resourceserver.model.lesson.Lesson;
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
    private String title;
    private String image;
    private int duration;
    private int point;
    private int numberOfKeys;

    @OneToMany(mappedBy = "question")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Answer> answers;

    private boolean disOrder;
    private boolean disFlg;
    private boolean overlooked;
    private Date updatedAt;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "category_id")
    private Category category;
}
