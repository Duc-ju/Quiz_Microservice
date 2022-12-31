package com.quizz.asynchronoustestservice.repository;

import com.quizz.asynchronoustestservice.model.SocketRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocketRegistrationRepository extends JpaRepository<SocketRegistration, Long> {

}
