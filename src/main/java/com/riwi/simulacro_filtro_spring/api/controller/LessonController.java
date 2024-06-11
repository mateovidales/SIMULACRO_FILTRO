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
import com.riwi.simulacro_filtro_spring.api.dto.request.LessonReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.LessonResp;
import com.riwi.simulacro_filtro_spring.infraestructure.abstract_service.ILessonService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Lessons")
@RestController
@RequestMapping("/lessons")
@AllArgsConstructor
public class LessonController {

    private final ILessonService lessonService;

    @Operation(summary = "Create lesson", 
    description = "create lesson")
    @ApiResponse(responseCode = "400", 
    description = "Invalid lesson",
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @PostMapping
    public ResponseEntity<LessonResp> createLesson(@Validated @RequestBody LessonReq request) {
        return ResponseEntity.ok(this.lessonService.create(request));
    }

    @Operation(summary = "Get lesson", 
    description = "get lesson")
    @ApiResponse(responseCode = "400",
    description = "Invalid lesson",
    content = @Content(mediaType = "application/json",
    schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/{lesson_id}")
    public ResponseEntity<LessonResp> getLessonInfo(@PathVariable("lesson_id") Long lessonId) {
        return ResponseEntity.ok(this.lessonService.get(lessonId));
    }

    @Operation(summary = "Update lesson",
    description = "update lesson")
    @ApiResponse(responseCode = "400",
    description = "Invalid lesson", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @PutMapping("/update/{lesson_id}")
    public ResponseEntity<LessonResp> updateLesson(@PathVariable("lesson_id") Long lessonId,
                                                   @Validated @RequestBody LessonReq request) {
        return ResponseEntity.ok(this.lessonService.update(request, lessonId));
    }

    @Operation(summary = "Delete lesson",
    description = "delete lesson")
    @ApiResponse(responseCode = "400", 
    description = "Invalid lesson ID", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @DeleteMapping("/delete/{lesson_id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable("lesson_id") Long lessonId) {
        this.lessonService.delete(lessonId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get lessons by course ID", 
    description = "get lessons by course id")
    @ApiResponse(responseCode = "400",
    description = "Invalid course",
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/course/{course_id}/lessons")
    public ResponseEntity<List<LessonResp>> getLessonsByCourse(@PathVariable("course_id") Long courseId) {
        return ResponseEntity.ok(this.lessonService.getLessonsByCourseId(courseId));
    }
}
