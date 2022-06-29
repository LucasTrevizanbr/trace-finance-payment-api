package com.payment.application.exception

import com.payment.application.controller.response.ErrorResponse
import com.payment.application.controller.response.FieldErrorResponse
import com.payment.application.enums.Errors
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.time.format.DateTimeParseException

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException, request: WebRequest) : ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            httpCode = HttpStatus.NOT_FOUND.value(),
            message = ex.message,
            internalCode = ex.errorCode,
            errors = null
        )

        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            httpCode = HttpStatus.UNPROCESSABLE_ENTITY.value(),
            message = Errors.TP001.message,
            internalCode = Errors.TP001.code,
            errors = ex.bindingResult.fieldErrors
                .map { FieldErrorResponse(it.defaultMessage ?: "invalid", it.field) }
        )

        return ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(DateTimeParseException::class)
    fun handleDateTimeParseException(ex: DateTimeParseException, request: WebRequest): ResponseEntity<ErrorResponse> {

        val error = ErrorResponse(
            httpCode = HttpStatus.BAD_REQUEST.value(),
            message = Errors.TP202.message,
            internalCode = Errors.TP202.code,
            errors = null
        )

        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

}


