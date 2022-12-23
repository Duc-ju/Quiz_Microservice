package com.quizz.roomservice.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Log4j2
public class AnswerTimeRepositoryCustomImpl implements AnswerTimeRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long countAnswerTime(Long lessonId) {
        final String sql = "SELECT COUNT(*) FROM `answer_time` WHERE LESSON_ID = ?";
        log.info(sql);
        return jdbcTemplate.queryForObject(sql, Long.class, lessonId);
    }
}
