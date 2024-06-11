package com.riwi.simulacro_filtro_spring.api.dto.request;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionReq {
    @NotBlank
    @Schema(description = "Content")
    private String content;

    @Schema(description = "Submission Date")
    private LocalDate submissionDate;
    
    @NotNull
    @Schema(description = "User ID", example = "1")
    private Long userId;

    @NotNull
    @Schema(description = "Assignment ID", example = "1")
    private Long assignmentId;    
}
