package org.example.jsonview.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.jsonview.exceptions.customexceptions.NotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ErrorResponse handleNotFoundExceptions(NotFoundException ex) {
        return ErrorResponse.builder()
                .errorCode(NOT_FOUND.value())
                .errorMessage(ex.getMessage())
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleValidationExceptions() {

        return ErrorResponse.builder()
                .errorCode(BAD_REQUEST.value())
                .errorMessage("Невозможно десериализовать запрос. Вероятно, ошибка в типе операции")
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleAllExceptionsBadRequest(ConstraintViolationException e) {
        return ErrorResponse.builder()
                .errorCode(BAD_REQUEST.value())
                .errorMessage(e.getMessage())
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException e) {
        String errorMessage = Optional.ofNullable(e.getBindingResult().getFieldError())
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Неизвестная ошибка");

        return ErrorResponse.builder()
                .errorCode(BAD_REQUEST.value())
                .errorMessage(errorMessage)
                .build();
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ErrorResponse handleUnknownException() {
        return ErrorResponse.builder()
                .errorCode(INTERNAL_SERVER_ERROR.value())
                .errorMessage("Неизвестная ошибка")
                .build();
    }


    @Builder
    @Getter
    @AllArgsConstructor
    public static class ErrorResponse {
        private int errorCode;
        private String errorMessage;
    }
}