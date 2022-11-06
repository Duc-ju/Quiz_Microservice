package com.quizz.socketservice.repository;

import com.quizz.socketservice.model.SocketRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocketRegistrationRepository extends JpaRepository<SocketRegistration, Long> {
    
}
