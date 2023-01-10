package com.quizz.notificationservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AnswerTimeNotificationRepositoryCustomImpl implements AnswerTimeNotificationRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void updateReadByUserId(String userId) {
        String sql = "UPDATE `NOTIFICATION` SET USER_READ=TRUE WHERE USER_ID = ?";
        jdbcTemplate.update(sql, userId);
    }
}
