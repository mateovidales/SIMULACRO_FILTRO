package com.riwi.simulacro_filtro_spring.api.dto.request;

import java.time.LocalDate;
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
public class AssignmentReq {
    @NotBlank
    @Size(min = 1, max = 100)
    @Schema(description = "Assignment title")
    private String assignmentTitle;

    @Schema(description = "Description")
    private String description;

    @NotNull
    @Schema(description = "Due date")
    private LocalDate dueDate;

    @NotNull
    @Schema(description = "Lesson ID", example = "1")
    private Long lessonId;    
}
