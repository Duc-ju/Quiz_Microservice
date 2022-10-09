package com.quizz.testservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LessonLike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long lessonId;
    private Long userId;
}
