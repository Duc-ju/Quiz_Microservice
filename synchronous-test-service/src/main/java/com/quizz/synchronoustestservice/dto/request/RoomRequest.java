package com.quizz.synchronoustestservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {
    private Long id;
    private Long lessonId;
    private Long userId;
    private LocalDateTime createdAt;
}
