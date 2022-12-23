package com.quizz.roomservice.service;

import com.quizz.roomservice.common.ResponseObject;
import com.quizz.roomservice.model.Room;
import com.quizz.roomservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final WebClient.Builder webClientBuilder;

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room getRoom(Long id) throws Exception {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if (!roomOptional.isPresent()) {
            throw new Exception("Cannot find room with id " + id);
        }
        return roomOptional.get();
    }

    public Object getLessonByRoomId(Long roomId) throws Exception {
        Room room = getRoom(roomId);
        ResponseObject lesson = webClientBuilder.build().get()
                .uri("lb://lesson-service/api/v1/resource/lessons/" + room.getLessonId())
                .retrieve().bodyToMono(ResponseObject.class)
                .block();
        return lesson.getData();
    }
}