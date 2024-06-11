package com.riwi.simulacro_filtro_spring.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.simulacro_filtro_spring.api.dto.errors.ErrorsResp;
import com.riwi.simulacro_filtro_spring.api.dto.request.CourseReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.CourseResp;
import com.riwi.simulacro_filtro_spring.infraestructure.abstract_service.ICourseService;
import com.riwi.simulacro_filtro_spring.utils.enums.SortType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Courses")
@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

    private final ICourseService courseService;

    @Operation(summary = "Create course",
    description = "create course")
    @ApiResponse(responseCode = "400", 
    description = "Invalid course", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @PostMapping
    public ResponseEntity<CourseResp> createCourse(@Validated @RequestBody CourseReq request) {
        return ResponseEntity.ok(this.courseService.create(request));
    }

    @Operation(summary = "Get course", 
    description = "get course")
    @ApiResponse(responseCode = "400", 
    description = "Invalid course", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping("/{course_id}")
    public ResponseEntity<CourseResp> getCourseInfo(@PathVariable("course_id") Long courseId) {
        return ResponseEntity.ok(this.courseService.get(courseId));
    }
    @Operation(summary = "Get all courses", 
    description = "get all courses")
    @ApiResponse(responseCode = "400", 
    description = "Invalid pagination", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @GetMapping
    public ResponseEntity<Page<CourseResp>> getAllCourses(@RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestHeader(required = false) SortType sortType) {
        if (sortType == null) sortType = SortType.NONE;
        return ResponseEntity.ok(this.courseService.getAll(page - 1, size, sortType));
    }

    @Operation(summary = "Update course", 
    description = "update course")
    @ApiResponse(responseCode = "400", 
    description = "Invalid course ID", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @PutMapping("/update/{course_id}")
    public ResponseEntity<CourseResp> updateCourse(@PathVariable("course_id") Long courseId,
                                                   @Validated @RequestBody CourseReq request) {
        return ResponseEntity.ok(this.courseService.update(request, courseId));
    }

    @Operation(summary = "Delete course", 
    description = "delete course")
    @ApiResponse(responseCode = "400", 
    description = "Invalid course", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorsResp.class)))
    @DeleteMapping("/delete/{course_id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("course_id") Long courseId) {
        this.courseService.delete(courseId);
        return ResponseEntity.noContent().build();
    }

}