package com.riwi.filtroSpringBoot.api.dto.request;

import java.util.List;

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
public class QuestionsRequest {


    @Schema(
        description = "the question", 
        example = "how is 2 + 2")                       // SWAGGER
    @NotBlank(
        message = "The question is required")           // validation
    private String text;
    private String type;
    @NotNull (
        message = "The state can bee null")             // validation
    private boolean active;
    @Schema(description = "List of option questions")
    private List<OptionQuestionRequest> options;

    
}
