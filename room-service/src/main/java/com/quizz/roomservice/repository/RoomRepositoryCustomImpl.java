package com.quizz.roomservice.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class RoomRepositoryCustomImpl implements RoomRepositoryCustom {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long countUserInRoomById(Long roomId) {
        final String sql = "SELECT COUNT(*) FROM `answer_time` WHERE ROOM_ID = ?";
        log.info(sql);
        return jdbcTemplate.queryForObject(sql, Long.class, roomId);
    }
}
