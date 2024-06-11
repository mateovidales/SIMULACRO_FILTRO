package com.riwi.simulacro_filtro_spring.api.dto.response;


import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResp 
{
    @Schema(description = "Message ID", example = "1")
    private Long messageId;

    @Schema(description = "Sender ID", example = "1")
    private Long senderId;

    @Schema(description = "Receiver ID", example = "2")
    private Long receiverId;

    @Schema(description = "Course ID", example = "1")
    private Long courseId;

    @Schema(description = "Message content")
    private String messageContent;

    @Schema(description = "Sent date")
    private LocalDate sentDate;
}
