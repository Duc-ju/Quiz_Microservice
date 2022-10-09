package com.quizz.resourceserver.model.lesson;

import com.quizz.resourceserver.model.question.Question;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Question> questions;
//    private String image;
}
