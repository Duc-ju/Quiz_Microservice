package com.quizz.notificationservice.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 2047)
    private String message;
    private String userId;
    private String RedirectUrl;
    private boolean userRead = false;
    private LocalDateTime createdTime;
}
