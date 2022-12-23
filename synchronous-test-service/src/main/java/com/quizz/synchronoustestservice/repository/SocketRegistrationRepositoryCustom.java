package com.quizz.synchronoustestservice.repository;

import java.util.List;

public interface SocketRegistrationRepositoryCustom {

    void killAllSocketRegistration(String socketId);

    List<String> getRoomListBySocketId(String socketId);

    Long countUserInRoom(String roomId);

    void updateRoomId(String socketId, String roomId);
}
