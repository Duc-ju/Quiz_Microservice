package com.quizz.socketservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerTime {
    private Long id;
    private Long lessonId;
    private Long userId;
    private LocalDateTime playedDateTime;
}
