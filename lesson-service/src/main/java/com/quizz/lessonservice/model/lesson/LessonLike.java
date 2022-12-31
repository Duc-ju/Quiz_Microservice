package com.quizz.lessonservice.model.lesson;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LessonLike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "lesson_id")
    @JsonBackReference
    private Lesson lesson;

    private LocalDateTime createdAt;
}
