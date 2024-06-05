package com.riwi.filtroSpringBoot.api.dto.response.SurveyResponse;


import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveyResponse {
    @Schema(description = "title of survey ") // SWAGGER
    private String title;
    @Schema(description = "description of survey") // SWAGGER
    private String description;
    @Schema(description = "date of creation  survey ") // SWAGGER
    private Date creationDate;
    @Schema(description = "state of survey") // SWAGGER
    private boolean active;
    
}
