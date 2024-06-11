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
public class LessonReq {
    @NotBlank
    @Size(min = 1, max = 100, message = "Title required")
    @Schema(description = "Lesson title")
    private String lessonTitle;

    @Schema(description = "Content")
    private String content;

    @NotNull(message = "Course ID Required")
    @Schema(description = "Course ID", example = "1")
    private Long courseId; 
}
