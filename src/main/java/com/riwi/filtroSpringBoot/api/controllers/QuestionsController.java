package com.riwi.filtroSpringBoot.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.filtroSpringBoot.api.dto.request.QuestionsRequest;
import com.riwi.filtroSpringBoot.api.dto.response.QuestionOptionsResponse.QuestionsResponse;
import com.riwi.filtroSpringBoot.infraestructure.abstract_services.IQuestionsService;
import com.riwi.filtroSpringBoot.util.enums.SortType;
import com.riwi.filtroSpringBoot.util.exceptions.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/questions")
@AllArgsConstructor
@Tag(name = "Questions Entity Controller")

public class QuestionsController {



    @Autowired
    private final IQuestionsService iQuestionsService;

    /*----------------------------
     * GET ALL
     * ----------------------------
     */

     @Operation(
        summary = "Displays all Questions",
        description = "Displays the Users in a list, it is configured to display 10 items per page."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "SUCCESSFUL"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "401", description = "NOT AUTHORIZED"),
        @ApiResponse(responseCode = "403", description = "FORBIDDEN ACCESS"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
     @GetMapping
    public ResponseEntity<Page<QuestionsResponse>> getAll(
            @Parameter(description = "Page number (default: 1)", example = "1") // SWAGGER
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Number of items per page (default: 10)", example = "10") // SWAGGER
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(this.iQuestionsService.getAll(page - 1, size, SortType.NONE));
    }


     /*------------------------------
     * GET BY ID
     * -----------------------------
     */

    @Operation(
        summary = "Displays one question by id",
        description = "Shows the question by the ID sent or requested by path,value cannot be less than 1."
    ) //SWAGGER
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "SUCCESSFUL"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "401", description = "NOT AUTHORIZED"),
        @ApiResponse(responseCode = "403", description = "FORBIDDEN ACCESS"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping(path = "/{question_id}")
    public ResponseEntity<QuestionsResponse> getById(
        @Parameter(description = "Question ID",example = "1") // SWAGGER
        @PathVariable Integer question_id) {

        QuestionsResponse question = this.iQuestionsService.getById(question_id);
        if (question == null) {
            throw new ResourceNotFoundException("Question not found");
        }
        return ResponseEntity.ok(question);
    }

     /*--------------------
     * CREATE QUESTION
     * -------------------
     */

    @Operation(
        summary = "creates a new question",
        description = "create a new question by entering the required data"
    ) //SWAGGER
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "SUCCESSFUL"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "401", description = "NOT AUTHORIZED"),
        @ApiResponse(responseCode = "403", description = "FORBIDDEN ACCESS"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping(path = "/create")
    public ResponseEntity<QuestionsResponse> create(@Validated @RequestBody QuestionsRequest request) {
        return ResponseEntity.ok(this.iQuestionsService.create(request));
    }

     /*----------------------
     * UPDATE QUESTION
     * ---------------------
     */

     @Operation(
        summary = "update  question by ID",
        description = "updates a previously created question and the ID and the new modified parameters must be sent through the Path, value cannot be less than 1"
    ) //SWAGGER
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "SUCCESSFUL"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "401", description = "NOT AUTHORIZED"),
        @ApiResponse(responseCode = "403", description = "FORBIDDEN ACCESS"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping(path = "/{question_id}")
    public ResponseEntity<QuestionsResponse> update(@Validated @RequestBody QuestionsRequest request, 
        @Parameter(description = "User ID",example = "1") // SWAGGER
        @PathVariable Integer question_id) {
        return ResponseEntity.ok(this.iQuestionsService.update(request, question_id));
    }


    
}
