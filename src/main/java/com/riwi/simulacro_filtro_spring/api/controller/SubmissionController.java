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
import com.riwi.simulacro_filtro_spring.api.dto.request.SubmissionReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.SubmissionResp;
import com.riwi.simulacro_filtro_spring.infraestructure.abstract_service.ISubmissionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Submissions")
@RestController
@RequestMapping("/submissions")
@AllArgsConstructor
public class SubmissionController {

    private final ISubmissionService submissionService;

    @Operation(summary = "Create submission",
    description = "createsubmission")
    @ApiResponse(responseCode = "400", 
    description = "Invalid", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @PostMapping
    public ResponseEntity<SubmissionResp> createSubmission(@Validated @RequestBody SubmissionReq request) {
        return ResponseEntity.ok(this.submissionService.create(request));
    }

    @Operation(summary = "Get submission information",
    description = "get submission")
    @ApiResponse(responseCode = "400", 
    description = "Invalid submission", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/{submission_id}")
    public ResponseEntity<SubmissionResp> getSubmissionInfo(@PathVariable("submission_id") Long submissionId) {
        return ResponseEntity.ok(this.submissionService.get(submissionId));
    }

    @Operation(summary = "Update submission", 
    description = "update submission")
    @ApiResponse(responseCode = "400", 
    description = "Invalid submission", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @PutMapping("/update/{submission_id}")
    public ResponseEntity<SubmissionResp> updateSubmission(@PathVariable("submission_id") Long submissionId,
                                                           @Validated @RequestBody SubmissionReq request) {
        return ResponseEntity.ok(this.submissionService.update(request, submissionId));
    }

    @Operation(summary = "Delete submission", 
    description = "delete submission")
    @ApiResponse(responseCode = "400", 
    description = "Invalid submission", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @DeleteMapping("/delete/{submission_id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable("submission_id") Long submissionId) {
        this.submissionService.delete(submissionId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get submissions by ID",
    description = "get all submissions")
    @ApiResponse(responseCode = "400", 
    description = "Invalid assignment ID",
    content = @Content(mediaType = "application/json",
    schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/assignment/{assignment_id}/submissions")
    public ResponseEntity<List<SubmissionResp>> getSubmissionsByAssignment(@PathVariable("assignment_id") Long assignmentId) {
        return ResponseEntity.ok(this.submissionService.getSubmissionsByAssignmentId(assignmentId));
    }

    @Operation(summary = "Get submissions by user ID",
    description = "get all by user")
    @ApiResponse(responseCode = "400",
    description = "Invalid user",
    content = @Content(mediaType = "application/json",
    schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/user/{user_id}/submissions")
    public ResponseEntity<List<SubmissionResp>> getSubmissionsByUser(@PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(this.submissionService.getSubmissionsByUserId(userId));
    }
}