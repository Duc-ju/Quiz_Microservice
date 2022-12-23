package com.quizz.livetestservice.repository;

import com.quizz.livetestservice.model.SocketRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocketRegistrationRepository extends JpaRepository<SocketRegistration, Long> {

}
