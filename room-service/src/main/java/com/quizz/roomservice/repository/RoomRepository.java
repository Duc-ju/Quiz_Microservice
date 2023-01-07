package com.quizz.roomservice.repository;

import com.quizz.roomservice.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List findAllByUserId(String userId);
}
