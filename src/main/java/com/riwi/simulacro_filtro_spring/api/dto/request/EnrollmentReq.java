package com.riwi.simulacro_filtro_spring.api.dto.request;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentReq {
   @NotNull
    @Schema(description = "User ID", example = "1")
    private Long userId;

    @Schema(description = "Enrollment Date")
    private LocalDate enrollmentDate;

    @NotNull
    @Schema(description = "Course ID", example = "1")
    private Long courseId;
}
