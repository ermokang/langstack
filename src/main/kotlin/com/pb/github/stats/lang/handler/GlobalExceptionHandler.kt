package com.pb.github.stats.lang.handler

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.Exception
import java.lang.invoke.MethodHandles

@ControllerAdvice
class GlobalExceptionHandler {
    private val logger: Logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

    @ExceptionHandler(Exception::class)
    fun handleGeneralExceptions(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Handling exception $e as ${HttpStatus.INTERNAL_SERVER_ERROR.name}")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse(e.message))
    }

    class ErrorResponse(val message: String? = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase)
}
