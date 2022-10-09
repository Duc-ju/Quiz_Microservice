package com.quizz.resourceserver.model.lesson;

import com.quizz.resourceserver.model.question.Question;
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
    private String title;

    @Transient
    private Float averageAccuracy;

    @Transient
    private Long numberOfPlayed;

    @Transient
    private Long numberOfLiked;

    @Transient
    @Getter(AccessLevel.NONE)
    private Integer numberOfQuestion;

    private Date createdAt;
    private Date updatedAt;
    private boolean disFlg;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @OneToMany(mappedBy = "lesson")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Question> questions;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Category category;

    @ManyToMany
    @JoinColumn(name = "tag_id")
    private Collection<Tag> tags;

    private String image;

    public Integer getNumberOfQuestion() {
        if (questions != null) return questions.size();
        return 0;
    }
}
