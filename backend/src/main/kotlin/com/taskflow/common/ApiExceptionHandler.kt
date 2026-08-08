package com.taskflow.common

import com.taskflow.task.TaskNotFoundException
import com.taskflow.task.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {
    @ExceptionHandler(TaskNotFoundException::class, UserNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFound(ex: RuntimeException) = mapOf("error" to (ex.message ?: "Resource not found"))

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequest(ex: IllegalArgumentException) = mapOf("error" to (ex.message ?: "Bad request"))

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun validation(ex: MethodArgumentNotValidException): Map<String, Any> = mapOf(
        "error" to "Validation failed",
        "fields" to ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "Invalid value") }
    )
}
