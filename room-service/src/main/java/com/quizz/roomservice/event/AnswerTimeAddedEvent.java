package com.quizz.roomservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerTimeAddedEvent {
    private Long roomId;
    private Long answerTimeId;
    private String lessonTitle;
    private Long point;
    private LocalDateTime completedTime;
}
