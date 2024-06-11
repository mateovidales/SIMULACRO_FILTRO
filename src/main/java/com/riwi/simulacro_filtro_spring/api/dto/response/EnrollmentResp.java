package com.riwi.simulacro_filtro_spring.api.dto.response;


import java.time.LocalDate;

import com.riwi.simulacro_filtro_spring.domain.entities.Course;
import com.riwi.simulacro_filtro_spring.domain.entities.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentResp {
    @Schema(description = "Enrollment ID", example = "1")
    private Long enrollmentId;

    @Schema(description = "User ID", example = "1")
    private User user;

    @Schema(description = "Course ID", example = "1")
    private Course course;

    @Schema(description = "Enrollment date")
    private LocalDate enrollmentDate;
}
