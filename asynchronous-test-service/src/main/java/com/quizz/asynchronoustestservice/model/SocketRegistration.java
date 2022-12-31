package com.quizz.asynchronoustestservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocketRegistration {

    private Long id;
    private Long roomId;
    private Long lessonId;
    private LocalDateTime joinedTime;
    private LocalDateTime closedTime;
    private boolean active;
}
