package com.riwi.simulacro_filtro_spring.api.dto.response;

import java.util.List;
import com.riwi.simulacro_filtro_spring.utils.enums.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResp 
{
    @Schema(description = "User ID", example = "1")
    private Long userId;

    @Schema(description = "Username")
    private String userName;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "Full name")
    private String fullName;

    @Schema(description = "User role")
    private Role role;

    @Schema(description = "List of enrollments of the user")
    private List<EnrollmentResp> enrollments;

    @Schema(description = "List of submissions of the user")
    private List<SubmissionResp> submissions;

    @Schema(description = "List of sent messages")
    private List<MessageResp> sentMessages;

    @Schema(description = "List of received messages")
    private List<MessageResp> receivedMessages;

    @Schema(description = "List of courses taught by the user")
    private List<CourseResp> courses;

}
