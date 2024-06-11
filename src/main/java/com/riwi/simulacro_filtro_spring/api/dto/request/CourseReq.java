package com.riwi.simulacro_filtro_spring.api.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseReq {
    @NotBlank(message = "Course name is required")
    @Size(min = 1, max = 100, message = "Course Name Between 1 and 100 characters")
    @Schema(description = "Course name", example = "Math")
    private String courseName;

    @Schema(description = "Course description")
    private String description;

    @NotNull
    @Schema(description = "Instructor ID", example = "1")
    private Long instructorId; 
}
