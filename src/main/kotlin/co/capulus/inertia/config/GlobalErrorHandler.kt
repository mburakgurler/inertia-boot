package co.capulus.inertia.config

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView

@ControllerAdvice
class GlobalErrorHandler(private val objectMapper: ObjectMapper) {

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception, request: HttpServletRequest): ModelAndView {
        val status = when (exception) {
            is SecurityException -> HttpStatus.FORBIDDEN
            is NoSuchElementException -> HttpStatus.NOT_FOUND
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        return inertia(
            component = "Error",
            props = mapOf(
                "status" to status.value(),
                "message" to (exception.message ?: "An error occurred")
            ),
            request = request,
            objectMapper = objectMapper
        )
    }
}