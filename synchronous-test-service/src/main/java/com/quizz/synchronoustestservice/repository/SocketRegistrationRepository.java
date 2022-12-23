package com.quizz.synchronoustestservice.repository;

import com.quizz.synchronoustestservice.model.SocketRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocketRegistrationRepository extends JpaRepository<SocketRegistration, Long> {

}
