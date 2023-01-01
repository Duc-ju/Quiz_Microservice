package com.quizz.lessonservice.model.lesson;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.quizz.lessonservice.model.question.Question;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 2048)
    private String title;

    @Column(length = 4096)
    private String description;

    private String ownerId;

    @Transient
    private Float averageAccuracy;

    @Transient
    private Long numberOfPlayed = Long.valueOf(0);

    @Transient
    @Getter(AccessLevel.NONE)
    private Integer numberOfQuestion;

    private String type;

    private Date createdAt;
    private Date updatedAt;
    private boolean disFlg;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @OneToMany(mappedBy = "lesson")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private Collection<Question> questions;

    @OneToMany(mappedBy = "lesson")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private Collection<LessonLike> lessonLikes;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private Category category;

    @OneToMany(mappedBy = "lesson")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference(value = "LessonTag_Lesson")
    private Collection<LessonTag> lessonTags;

    @Column(length = 1024)
    private String image;

    public Integer getNumberOfQuestion() {
        if (questions != null) return questions.size();
        return 0;
    }
}
