package com.riwi.simulacro_filtro_spring.infraestructure.services;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.simulacro_filtro_spring.api.dto.request.MessageReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.MessageResp;
import com.riwi.simulacro_filtro_spring.domain.entities.Course;
import com.riwi.simulacro_filtro_spring.domain.entities.Message;
import com.riwi.simulacro_filtro_spring.domain.entities.User;
import com.riwi.simulacro_filtro_spring.domain.repositories.CourseRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.MessageRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.UserRepository;
import com.riwi.simulacro_filtro_spring.infraestructure.abstract_service.IMessageService;
import com.riwi.simulacro_filtro_spring.utils.enums.SortType;
import com.riwi.simulacro_filtro_spring.utils.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageService implements IMessageService {

    private static final String FIELD_BY_SORT = "sentDate";

    @Autowired
    private final MessageRepository messageRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CourseRepository courseRepository;

    @Override
    public MessageResp create(MessageReq request) {
        Message message = this.requestToEntity(request);
        return this.entityToResp(this.messageRepository.save(message));
    }

    @Override
    public MessageResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public MessageResp update(MessageReq request, Long id) {
        Message message = this.find(id);

        message= this.requestToEntity(request);
        message.setId(id);

        return this.entityToResp(this.messageRepository.save(message));
    }

    @Override
    public void delete(Long id) {
        Message message = this.find(id);
        this.messageRepository.delete(message);
    }

    @Override
    public Page<MessageResp> getAll(int page, int size, SortType sortType) {
        if (page < 0) page = 0;

        PageRequest pagination = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        };

        return this.messageRepository.findAll(pagination).map(this::entityToResp);
    }
    
    private Message find(Long id) {
        return this.messageRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No message found"));
    }

    
    @Override
    public List<MessageResp> getMessagesByCourseId(Long courseId) {
        List<Message> messages = this.messageRepository.findByCourseId(courseId);
        return messages.stream().map(this::entityToResp).collect(Collectors.toList());
    }

    @Override
public List<MessageResp> getMessagesBetweenUsers(Long senderId, Long receiverId) {
    List<Message> messagesBetweenUsers = this.messageRepository.findBySenderAndReceiver(senderId, receiverId);
    return messagesBetweenUsers.stream()
            .map(this::entityToResp)
            .collect(Collectors.toList());
}

    private MessageResp entityToResp(Message entity) {
        return MessageResp.builder()
                .messageId(entity.getId())
                .senderId(entity.getSender().getId())
                .receiverId(entity.getReceiver().getId())
                .courseId(entity.getCourse().getId())
                .messageContent(entity.getMessageContent())
                .sentDate(entity.getSentDate())
                .build();
    }

    private Message requestToEntity(MessageReq messageReq) {
        User sender = this.userRepository.findById(messageReq.getSenderId()).orElseThrow(() -> new BadRequestException("No Sender found"));

        User receiver = this.userRepository.findById(messageReq.getReceiverId()).orElseThrow(() -> new BadRequestException("No Receiver found"));

        Course course = this.courseRepository.findById(messageReq.getCourseId()).orElseThrow(() -> new BadRequestException("No Course found"));

        return Message.builder()
                .sender(sender)
                .receiver(receiver)
                .course(course)
                .messageContent(messageReq.getMessageContent())
                .sentDate(messageReq.getSentDate())
                .build();
    }

}