package com.riwi.simulacro_filtro_spring.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.simulacro_filtro_spring.api.dto.errors.ErrorsResp;
import com.riwi.simulacro_filtro_spring.api.dto.request.EnrollmentReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.EnrollmentResp;
import com.riwi.simulacro_filtro_spring.infraestructure.abstract_service.IEnrollmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Enrollments")
@RestController
@RequestMapping("/enrollments")
@AllArgsConstructor
public class EnrollmentController {

    private final IEnrollmentService enrollmentService;

    @Operation(summary = "Enrollment user in course",
    description = "enrollment user in course")
    @ApiResponse(responseCode = "400",
    description = "Invalid enrollment",
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @PostMapping
    public ResponseEntity<EnrollmentResp> enrollUser(@Validated @RequestBody EnrollmentReq request) {
        return ResponseEntity.ok(this.enrollmentService.create(request));
    }
    
    @Operation(summary = "Get courses by user ID", 
    description = "get courses by user id")
    @ApiResponse(responseCode = "400",
    description = "Invalid user", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/user/{user_id}/courses")
    public ResponseEntity<List<EnrollmentResp>> getCoursesByUser(@PathVariable("user_id") Long userId) {
    return ResponseEntity.ok(this.enrollmentService.getCoursesByUserId(userId));
    }

    @Operation(summary = "Get enrollment",
    description = "get enrollment")
    @ApiResponse(responseCode = "400",
    description = "Invalid enrollment", 
    content = @Content(mediaType = "application/json",
    schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/{enrollment_id}")
    public ResponseEntity<EnrollmentResp> getEnrollmentInfo(@PathVariable("enrollment_id") Long enrollmentId) {
        return ResponseEntity.ok(this.enrollmentService.get(enrollmentId));
    }

    @Operation(summary = "Delete enrollment", 
    description = "delete enrollment")
    @ApiResponse(responseCode = "400",
    description = "Invalid enrollment",
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @DeleteMapping("/delete/{enrollment_id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable("enrollment_id") Long enrollmentId) {
        this.enrollmentService.delete(enrollmentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get users by course ID",
    description = "get users by course id")
    @ApiResponse(responseCode = "400", 
    description = "Invalid course", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/course/{course_id}/users")
    public ResponseEntity<List<EnrollmentResp>> getUsersByCourse(@PathVariable("course_id") Long courseId) {
        return ResponseEntity.ok(this.enrollmentService.getUsersByCourseId(courseId));
    }
}