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
public class MessageReq {
     @NotNull(message = "Sender ID required")
    @Schema(description = "Sender ID", example = "1")
    private Long senderId;

    @NotNull(message = "Receiver ID required")
    @Schema(description = "Receiver ID", example = "2")
    private Long receiverId;

    @NotNull(message = "Course ID required")
    @Schema(description = "Course ID", example = "1")
    private Long courseId;

    @Schema(description = "Message content")
    private String messageContent;

    @Schema(description = "Message send Date")
    private LocalDate sentDate;
}
