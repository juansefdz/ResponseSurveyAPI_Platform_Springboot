package com.riwi.filtroSpringBoot.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {


    @Schema(
        description = "Name of the user", 
        example = "john_doe")                                               // SWAGGER
    @NotBlank(
        message = "The name is required")                                   // validation
     @Size(
        max = 50, 
        message = "The name must have a maximum of 100 characters")         // validation
    private String name;
    @Schema(
        description = "Email of the user", 
        example = "john@example.com")                                       // SWAGGER
    @NotBlank(
        message = "The email is required")                                  // validation
    @Email (message = "the email must be valid")                            // validation
    private String email;
    @Schema(
        description = "Password of the user")                               // SWAGGER
    @NotBlank(
        message = "The password is required")                               // validation
    private String password;
    @Schema(
        description = "state of the user")                                  // SWAGGER
    private boolean active;
    
}
