package com.quizz.socketservice.service;

import com.quizz.socketservice.dto.question.Question;
import com.quizz.socketservice.dto.response.ResponseObject;
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
                .uri("lb://resource-service/api/v1/resource/lessons/" + lessonId + "/questions")
                .retrieve().bodyToMono(new ParameterizedTypeReference<ResponseObject<List<Question>>>() {
                })
                .block();
        return questions.getData();
    }
}
