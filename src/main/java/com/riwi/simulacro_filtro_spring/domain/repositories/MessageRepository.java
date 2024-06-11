package com.riwi.simulacro_filtro_spring.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.riwi.simulacro_filtro_spring.domain.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findBySenderAndReceiver(Long senderId, Long receiverId);
    List<Message> findBySenderId(Long id);
    List<Message> findByReceiverId(Long id);
    List<Message> findByCourseId(Long id);
}