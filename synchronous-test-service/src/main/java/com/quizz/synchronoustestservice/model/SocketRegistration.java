package com.quizz.synchronoustestservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class SocketRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String socketId;
    private Long roomId;
    private Long lessonId;
    private LocalDateTime joinedTime;
    private LocalDateTime closedTime;
    private boolean active;
}
