package com.riwi.simulacro_filtro_spring.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.simulacro_filtro_spring.api.dto.errors.ErrorsResp;
import com.riwi.simulacro_filtro_spring.api.dto.request.AssignmentReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.AssignmentResp;
import com.riwi.simulacro_filtro_spring.infraestructure.abstract_service.IAssignmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Assignments")
@RestController
@RequestMapping("/assignments")
@AllArgsConstructor
public class AssignmentController {

    private final IAssignmentService assignmentService;

    @Operation(summary = "Create assignment", 
    description = "create assignment")
    @ApiResponse(responseCode = "400", 
    description = "Invalid assignment", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @PostMapping
    public ResponseEntity<AssignmentResp> createAssignment(@Validated @RequestBody AssignmentReq request) {
        return ResponseEntity.ok(this.assignmentService.create(request));
    }

    @Operation(summary = "Get assignment", 
    description = "get assignment")
    @ApiResponse(responseCode = "400", 
    description = "Invalid assignment", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/{assignment_id}")
    public ResponseEntity<AssignmentResp> getAssignmentInfo(@PathVariable("assignment_id") Long assignmentId) {
        return ResponseEntity.ok(this.assignmentService.get(assignmentId));
    }

    @Operation(summary = "Update assignment", 
    description = "update assignment")
    @ApiResponse(responseCode = "400", 
    description = "Invalid assignment", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @PutMapping("/update/{assignment_id}")
    public ResponseEntity<AssignmentResp> updateAssignment(@PathVariable("assignment_id") Long assignmentId,
                                                           @Validated @RequestBody AssignmentReq request) {
        return ResponseEntity.ok(this.assignmentService.update(request, assignmentId));
    }

    @Operation(summary = "Delete assignment", 
    description = "delete assignment")
    @ApiResponse(responseCode = "400", 
    description = "Invalid assignment", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @DeleteMapping("/delete/{assignment_id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable("assignment_id") Long assignmentId) {
        this.assignmentService.delete(assignmentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get assignments by lesson", 
    description = "get assignment by lesson")
    @ApiResponse(responseCode = "400", 
    description = "Invalid lesson ID", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/lesson/{lesson_id}/assignments")
    public ResponseEntity<List<AssignmentResp>> getAssignmentsByLesson(@PathVariable("lesson_id") Long lessonId) {
        return ResponseEntity.ok(this.assignmentService.getAssignmentsByLessonId(lessonId));
    }
}