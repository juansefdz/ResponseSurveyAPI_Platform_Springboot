package com.riwi.filtroSpringBoot.api.dto.response.UserResponse;




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
public class SurveyResponseInUser {

    @Schema(description = "ID of the survey") // SWAGGER
    private int idSurvey;
    @Schema(description = "title of  the survey") // SWAGGER
    private String title;
    @Schema(description = "description of  the survey") // SWAGGER
    private String description;
    @Schema(description = "Date of  the survey") // SWAGGER
    private Date creationDate;
    @Schema(description = "status of  the survey") // SWAGGER
    private boolean active;
    
}
