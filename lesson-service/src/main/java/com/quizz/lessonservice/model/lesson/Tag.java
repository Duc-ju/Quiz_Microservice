package com.quizz.lessonservice.model.lesson;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 2048)
    private String name;

    @Column(length = 1024)
    private String image;

    @OneToMany(mappedBy = "tag")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference(value = "LessonTag_Tag")
    private Collection<LessonTag> lessonTags;
}
