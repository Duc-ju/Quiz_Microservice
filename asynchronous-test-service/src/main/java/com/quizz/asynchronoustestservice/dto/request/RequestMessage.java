package com.quizz.asynchronoustestservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestMessage<T> {
    private String type;
    private T body;
}
