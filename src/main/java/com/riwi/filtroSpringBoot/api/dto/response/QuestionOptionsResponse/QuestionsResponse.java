package com.riwi.filtroSpringBoot.api.dto.response.QuestionOptionsResponse;

import java.util.List;

import com.riwi.filtroSpringBoot.api.dto.response.QuestionOptionsResponse.OptionQuestionResponseInQuestion.OptionsQResponseInQuestion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsResponse {
    @Schema(description = "Id Question") // SWAGGER
    private int idQuestion;
    @Schema(description = "text of question") // SWAGGER
    private String text;
    @Schema(description = "Type of Question") // SWAGGER
    private String type;
    @Schema(description = "state of question") // SWAGGER
    private boolean active;
    @Schema(description = "Option Question in Question") // SWAGGER
    private List <OptionsQResponseInQuestion> optionQuestions;
}
