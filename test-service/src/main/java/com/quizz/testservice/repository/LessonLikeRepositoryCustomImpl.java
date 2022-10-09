package com.quizz.testservice.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class LessonLikeRepositoryCustomImpl implements LessonLikeRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long countLessonLike(Long lessonId) {
        final String sql = "SELECT COUNT(*) FROM `lesson_like` WHERE LESSON_ID = ?";
        log.info(sql);
        return jdbcTemplate.queryForObject(sql, Long.class, lessonId);
    }
}
