package com.taskflow.common

import com.taskflow.task.TaskNotFoundException
import com.taskflow.user.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException::class, UserNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFound(ex: RuntimeException) = ApiError(404, ex.message ?: "Resource not found")

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequest(ex: IllegalArgumentException) = ApiError(400, ex.message ?: "Invalid request")

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun validation(ex: MethodArgumentNotValidException): ApiError {
        val message = ex.bindingResult.fieldErrors.firstOrNull()?.defaultMessage ?: "Validation failed"
        return ApiError(400, message)
    }
}
