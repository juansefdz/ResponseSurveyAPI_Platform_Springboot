package com.riwi.filtroSpringBoot.api.dto.request;


import java.util.Date;

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
public class SurveyRequest {
    
    @Schema(
        description = "Name of the user", 
        example = "john_doe")                       // SWAGGER
    @NotBlank(
        message = "The name is required")           // validation
     @Size(
        max = 50, 
        message = "The name must have a maximum of 100 characters")   
    private String title; 
    private String description;
    private Date creationDate;
    @NotNull (
        message = "The state can bee null")         // validation
    private boolean active;

    private int Userid;
    
}
