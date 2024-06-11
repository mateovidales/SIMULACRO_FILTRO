package com.riwi.simulacro_filtro_spring.api.dto.response;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseResp {
    @Schema(description = "Course ID", example = "1")
    private Long courseId;

    @Schema(description = "Course name")
    private String courseName;

    @Schema(description = "Course description")
    private String description;

    @Schema(description = "Instructor ID", example = "1")
    private Long instructorId;   
    
    @Schema(description = "List of lessons in the course")
    private List<LessonResp> lessons;

    @Schema(description = "List of messages related to the course")
    private List<MessageResp> messages;

    @Schema(description = "List of enrollments in the course")
    private List<EnrollmentResp> enrollments;
}
