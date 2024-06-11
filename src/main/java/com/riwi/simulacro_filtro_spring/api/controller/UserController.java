package com.riwi.simulacro_filtro_spring.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.simulacro_filtro_spring.api.dto.request.UserReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.UserResp;
import com.riwi.simulacro_filtro_spring.infraestructure.abstract_service.IUserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Users")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    @Operation(summary = "Create user", description = "create new user")
    @ApiResponse(responseCode = "400", 
    description = "Invalid input",
     content = @Content (mediaType = "application/json", 
     schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping
    public ResponseEntity<UserResp> register(@Validated @RequestBody UserReq request) {
        return ResponseEntity.ok(this.userService.create(request));
    }

    @Operation(summary = "Get user id", description = "get user id")
    @ApiResponse(responseCode = "400", description = "Invalid user ID", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/{user_id}")
    public ResponseEntity<UserResp> getUserInfo(@PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(this.userService.get(userId));
    }

    @Operation(summary = "Update user", description = "update user")
    @ApiResponse(responseCode = "400", description = "Invalid user ID",
     content = @Content(mediaType = "application/json",
      schema = @Schema(implementation = ErrorResponse.class)))
    @PutMapping("/update/{user_id}")
    public ResponseEntity<UserResp> updateUser(@PathVariable("user_id") Long userId,
                                               @Validated @RequestBody UserReq request) {
        return ResponseEntity.ok(this.userService.update(request, userId));
    }

    @Operation(summary = "Delete user", description = " delete user")
    @ApiResponse(responseCode = "400", 
    description = "Invalid user ID", 
    content = @Content(mediaType = "application/json", 
    schema = @Schema(implementation = ErrorResponse.class)))
    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("user_id") Long userId) {
        this.userService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}