package com.riwi.filtroSpringBoot.api.error_handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.riwi.filtroSpringBoot.api.dto.errors.BaseErrorResponse;
import com.riwi.filtroSpringBoot.api.dto.errors.ListErrorsResponse;
import com.riwi.filtroSpringBoot.util.exceptions.ResourceNotFoundException;
import com.riwi.filtroSpringBoot.util.exceptions.UnauthorizedException;

                                         

// Clase GlobalExceptionHandler para manejar excepciones a nivel global en toda la aplicación
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejador para excepciones de validación de argumentos (MethodArgumentNotValidException)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ListErrorsResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Crear una lista para almacenar los detalles de los errores
        List<Map<String, String>> errors = new ArrayList<>();

        // Recorrer los errores de campo y agregarlos a la lista de errores
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("field", error.getField());
            errorDetails.put("error", error.getDefaultMessage());
            errors.add(errorDetails);
        }

        // Devolver una respuesta con los detalles de los errores
        return ListErrorsResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .errors(errors)
                .message("Validation failed for one or more fields. Please check the provided data.")
                .build();
    }

    // Manejador para excepciones de tipo BadRequestException
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseErrorResponse handleBadRequestException(BadRequestException ex) {
        // Devolver una respuesta de error con un mensaje personalizado
        return BaseErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .message("The request could not be understood or was missing required parameters.")
                .build();
    }

    // Manejador para excepciones de tipo ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex) {
        // Devolver una respuesta de error con un mensaje personalizado
        return BaseErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.name())
                .message("The requested resource was not found.")
                .build();
    }

    // Manejador para excepciones de tipo UnauthorizedException
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseErrorResponse handleUnauthorizedException(UnauthorizedException ex) {
        // Devolver una respuesta de error con un mensaje personalizado
        return BaseErrorResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .status(HttpStatus.UNAUTHORIZED.name())
                .message("You are not authorized to perform this action.")
                .build();
    }
}
