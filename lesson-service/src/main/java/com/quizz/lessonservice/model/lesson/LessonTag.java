package com.quizz.lessonservice.model.lesson;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LessonTag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference(value = "LessonTag_Lesson")
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference(value = "LessonTag_Tag")
    private Tag tag;
}
