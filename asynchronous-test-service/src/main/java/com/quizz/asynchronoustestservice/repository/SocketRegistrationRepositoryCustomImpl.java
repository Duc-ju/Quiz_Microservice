package com.quizz.asynchronoustestservice.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Log4j2
public class SocketRegistrationRepositoryCustomImpl implements SocketRegistrationRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void killAllSocketRegistration(String socketId) {
        final String sql = "UPDATE `SOCKET_REGISTRATION` SET `ACTIVE`= ?,`CLOSED_TIME`= ? WHERE SOCKET_ID = ?";
        log.info(sql);
        jdbcTemplate.update(sql, false, LocalDateTime.now(), socketId);
    }

    @Override
    public List<String> getRoomListBySocketId(String socketId) {
        final String sql = "SELECT ROOM_ID FROM `SOCKET_REGISTRATION` WHERE SOCKET_ID = ?";
        log.info(sql);
        return jdbcTemplate.queryForList(sql, String.class, socketId);
    }

    @Override
    public Long countUserInRoom(String roomId) {
        final String sql = "SELECT COUNT(*) FROM `SOCKET_REGISTRATION` WHERE ROOM_ID = ? AND ACTIVE = TRUE";
        log.info(sql);
        return jdbcTemplate.queryForObject(sql, Long.class, roomId);
    }

    @Override
    public void updateRoomId(String socketId, String roomId) {
        final String sql = "SELECT COUNT(*) FROM `SOCKET_REGISTRATION` WHERE ROOM_ID = ? AND ACTIVE = TRUE";
        log.info(sql);
        jdbcTemplate.update(sql, roomId);
    }

}
