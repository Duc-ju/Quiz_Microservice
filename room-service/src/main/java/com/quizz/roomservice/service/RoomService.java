package com.quizz.roomservice.service;

import com.quizz.roomservice.common.ResponseObject;
import com.quizz.roomservice.model.Room;
import com.quizz.roomservice.repository.RoomRepository;
import com.quizz.roomservice.repository.RoomRepositoryCustomImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    private final RoomRepositoryCustomImpl roomRepositoryCustom;
    private final WebClient.Builder webClientBuilder;

    public Room createRoom(Room room) {
        room.setCreatedAt(LocalDateTime.now());
        return roomRepository.save(room);
    }

    public Room getRoomById(Long id) throws Exception {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if (!roomOptional.isPresent()) {
            throw new Exception("Cannot find room with id " + id);
        }
        return roomOptional.get();
    }

    public Object getLessonByRoomId(Long roomId) throws Exception {
        Room room = getRoomById(roomId);
        ResponseObject lesson = webClientBuilder.build().get()
                .uri("lb://lesson-service/api/v1/resource/lessons/" + room.getLessonId() + "/raw")
                .retrieve().bodyToMono(ResponseObject.class)
                .block();
        return lesson.getData();
    }

    public List<Room> findAllByUserId(String userId) {
        return roomRepository.findAllByUserId(userId);
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public boolean checkStarted(Long roomId) {
        long numberOfUser = roomRepositoryCustom.countUserInRoomById(roomId);
        return numberOfUser > 0;
    }
}
