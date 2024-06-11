package com.riwi.simulacro_filtro_spring.api.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.simulacro_filtro_spring.api.dto.errors.ErrorsResp;
import com.riwi.simulacro_filtro_spring.api.dto.request.MessageReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.MessageResp;
import com.riwi.simulacro_filtro_spring.infraestructure.abstract_service.IMessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Messages")
@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {

    private final IMessageService messageService;

    @Operation(summary = "Send message",
    description = "send message")
    @ApiResponse(responseCode = "400",
    description = "Invalid message",
    content = @Content(mediaType = "application/json",
    schema = @Schema(implementation = ErrorsResp.class)))
    @PostMapping(path = "/send")
    public ResponseEntity<MessageResp> sendMessage(@Validated @RequestBody MessageReq request) {
        return ResponseEntity.ok(this.messageService.create(request));
    }

    @Operation(summary = "Get messages by course ID",
    description = "get all messages ")
    @ApiResponse(responseCode = "400",
    description = "Invalid message", 
    content = @Content(mediaType = "application/json",
    schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/course/{course_id}")
    public ResponseEntity<List<MessageResp>> getMessagesByCourse(@PathVariable("course_id") Long courseId) {
        return ResponseEntity.ok(this.messageService.getMessagesByCourseId(courseId));
    }

    @Operation(summary = "Get messages for users", 
    description = "get messages for users")
    @ApiResponse(responseCode = "400", 
    description = "Invalid message for users id", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping
    public ResponseEntity<List<MessageResp>> getMessagesBetweenUsers(@RequestParam("sender_id") Long senderId,
                                                                     @RequestParam("receiver_id") Long receiverId) {
        return ResponseEntity.ok(this.messageService.getMessagesBetweenUsers(senderId, receiverId));
    }
}