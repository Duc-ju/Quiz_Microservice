package com.quizz.synchronoustestservice.service;

import com.quizz.synchronoustestservice.dto.question.Question;
import com.quizz.synchronoustestservice.dto.response.ResponseObject;
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
                .uri("lb://lesson-service/api/v1/resource/lessons/" + lessonId + "/questions")
                .retrieve().bodyToMono(new ParameterizedTypeReference<ResponseObject<List<Question>>>() {
                })
                .block();
        return questions.getData();
    }
}
