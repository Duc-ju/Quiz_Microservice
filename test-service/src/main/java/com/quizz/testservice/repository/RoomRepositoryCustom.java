package com.quizz.testservice.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepositoryCustom {

    Long countUserInRoomById(Long roomId);
    
}
