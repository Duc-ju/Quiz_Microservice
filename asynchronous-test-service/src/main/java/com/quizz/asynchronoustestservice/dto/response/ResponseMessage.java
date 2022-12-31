package com.quizz.asynchronoustestservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMessage {
    private String status;

    private String type;

    @Nullable
    private Object message;
}
