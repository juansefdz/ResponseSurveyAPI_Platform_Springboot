package com.riwi.filtroSpringBoot.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.riwi.filtroSpringBoot.api.dto.request.UserRequest;
import com.riwi.filtroSpringBoot.api.dto.response.UserResponse.UserResponse;
import com.riwi.filtroSpringBoot.infraestructure.abstract_services.IUserEntityService;
import com.riwi.filtroSpringBoot.util.enums.SortType;
import com.riwi.filtroSpringBoot.util.exceptions.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
@Tag(name = "User Entity Controller")
public class UserEntityController {


    @Autowired
    private final IUserEntityService iUserEntityService;

    /*----------------------------
     * GET ALL
     * ----------------------------
     */

     @Operation(
        summary = "Displays all Users",
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
    public ResponseEntity<Page<UserResponse>> getAll(
            @Parameter(description = "Page number (default: 1)", example = "1") // SWAGGER
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Number of items per page (default: 10)", example = "10") // SWAGGER
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(this.iUserEntityService.getAll(page - 1, size, SortType.NONE));
    }


     /*------------------------------
     * GET BY ID
     * -----------------------------
     */

    @Operation(
        summary = "Displays one user by id",
        description = "Shows the user by the ID sent or requested by path,value cannot be less than 1."
    ) //SWAGGER
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "SUCCESSFUL"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "401", description = "NOT AUTHORIZED"),
        @ApiResponse(responseCode = "403", description = "FORBIDDEN ACCESS"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping(path = "/{user_id}")
    public ResponseEntity<UserResponse> getById(
        @Parameter(description = "User ID",example = "1") // SWAGGER
        @PathVariable Integer user_id) {

        UserResponse user = this.iUserEntityService.getById(user_id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return ResponseEntity.ok(user);
    }

     /*--------------------
     * CREATE USER
     * -------------------
     */

    @Operation(
        summary = "creates a new user",
        description = "create a new user by entering the required data"
    ) //SWAGGER
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "SUCCESSFUL"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "401", description = "NOT AUTHORIZED"),
        @ApiResponse(responseCode = "403", description = "FORBIDDEN ACCESS"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping(path = "/create")
    public ResponseEntity<UserResponse> create(@Validated @RequestBody UserRequest request) {
        return ResponseEntity.ok(this.iUserEntityService.create(request));
    }

     /*----------------------
     * UPDATE USER
     * ---------------------
     */

     @Operation(
        summary = "update  user by ID",
        description = "updates a previously created user and the ID and the new modified parameters must be sent through the Path, value cannot be less than 1"
    ) //SWAGGER
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "SUCCESSFUL"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "401", description = "NOT AUTHORIZED"),
        @ApiResponse(responseCode = "403", description = "FORBIDDEN ACCESS"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping(path = "/{user_id}")
    public ResponseEntity<UserResponse> update(@Validated @RequestBody UserRequest request, 
        @Parameter(description = "User ID",example = "1") // SWAGGER
        @PathVariable Integer user_id) {
        return ResponseEntity.ok(this.iUserEntityService.update(request, user_id));
    }



    
}
