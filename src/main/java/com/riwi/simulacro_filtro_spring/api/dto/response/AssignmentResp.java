package com.riwi.simulacro_filtro_spring.api.dto.response;

import java.time.LocalDate;
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
public class AssignmentResp {
      @Schema(description = "Assignment ID", example = "1")
    private Long assignmentId;

    @Schema(description = "Assignment title")
    private String assignmentTitle;

    @Schema(description = "Description")
    private String description;

    @Schema(description = "Due date")
    private LocalDate dueDate;

    @Schema(description = "Lesson ID", example = "1")
    private Long lessonId;    

    @Schema(description = "List of submissions for the assignment")
    private List<SubmissionResp> submissions;
}
