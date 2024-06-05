package com.riwi.filtroSpringBoot.api.dto.request;

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
public class OptionQuestionRequest {


    
    @Schema(
        description = "the answer of the question", 
        example = "a)2 b)4 c)1")                        // SWAGGER
    @NotBlank(
        message = "The question is required")           // validation
    private String text;
    @NotNull (
        message = "The state can bee null")             // validation
    private boolean active;
    private int idQuestion;
    
}
