package com.riwi.filtroSpringBoot.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.filtroSpringBoot.api.dto.request.SurveyRequest;
import com.riwi.filtroSpringBoot.api.dto.response.SurveyResponse.SurveyResponse;
import com.riwi.filtroSpringBoot.infraestructure.abstract_services.ISurveyService;

import com.riwi.filtroSpringBoot.util.enums.SortType;
import com.riwi.filtroSpringBoot.util.exceptions.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/surveys")
@AllArgsConstructor
@Tag(name = "Survey Entity Controller")

public class SurveyController {



    @Autowired
    private final ISurveyService iSurveyService;

    /*----------------------------
     * GET ALL
     * ----------------------------
     */

     @Operation(
        summary = "Displays all surveys",
        description = "Displays the surveys in a list, it is configured to display 10 items per page."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "SUCCESSFUL"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "401", description = "NOT AUTHORIZED"),
        @ApiResponse(responseCode = "403", description = "FORBIDDEN ACCESS"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
     @GetMapping
    public ResponseEntity<Page<SurveyResponse>> getAll(
            @Parameter(description = "Page number (default: 1)", example = "1") // SWAGGER
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Number of items per page (default: 10)", example = "10") // SWAGGER
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(this.iSurveyService.getAll(page - 1, size, SortType.NONE));
    }


     /*------------------------------
     * GET BY ID
     * -----------------------------
     */

    @Operation(
        summary = "Displays one survey by id",
        description = "Shows the survey by the ID sent or requested by path,value cannot be less than 1."
    ) //SWAGGER
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "SUCCESSFUL"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "401", description = "NOT AUTHORIZED"),
        @ApiResponse(responseCode = "403", description = "FORBIDDEN ACCESS"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping(path = "/{survey_id}")
    public ResponseEntity<SurveyResponse> getById(
        @Parameter(description = "Survey ID",example = "1") // SWAGGER
        @PathVariable Integer survey_id) {

        SurveyResponse survey = this.iSurveyService.getById(survey_id);
        if (survey == null) {
            throw new ResourceNotFoundException("Survey not found");
        }
        return ResponseEntity.ok(survey);
    }

     /*--------------------
     * CREATE SURVEY
     * -------------------
     */

    @Operation(
        summary = "creates a new survey",
        description = "create a new survey by entering the required data"
    ) //SWAGGER
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "SUCCESSFUL"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "401", description = "NOT AUTHORIZED"),
        @ApiResponse(responseCode = "403", description = "FORBIDDEN ACCESS"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping(path = "/create")
    public ResponseEntity<SurveyResponse> create(@Validated @RequestBody SurveyRequest request) {
        return ResponseEntity.ok(this.iSurveyService.create(request));
    }

    
}
