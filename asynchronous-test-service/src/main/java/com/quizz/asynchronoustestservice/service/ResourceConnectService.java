package com.quizz.asynchronoustestservice.service;

import com.quizz.asynchronoustestservice.dto.question.Question;
import com.quizz.asynchronoustestservice.dto.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceConnectService {

    private final WebClient.Builder webClientBuilder;

    public List<Question> getQuestionList(Long lessonId) {
        ResponseObject<List<Question>> questions = webClientBuilder.build().get()
                .uri("lb://lesson-service/api/v1/lesson/lessons/" + lessonId + "/questions")
                .retrieve().bodyToMono(new ParameterizedTypeReference<ResponseObject<List<Question>>>() {
                })
                .block();
        return questions.getData();
    }

    public Object getRoomStatistic(Long roomId) {
        ResponseObject questions = webClientBuilder.build().get()
                .uri("lb://room-service/api/v1/room/statistic/rooms/" + roomId)
                .retrieve().bodyToMono(ResponseObject.class)
                .block();
        return questions.getData();
    }
}
