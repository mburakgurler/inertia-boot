package co.capulus.inertia.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

private const val X_INERTIA = "X-Inertia"

@Component
class InertiaMiddleware : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        request.getHeader(X_INERTIA)?.let {
            response.apply {
                setHeader("Vary", "Accept")
                setHeader(X_INERTIA, "true")
                contentType = "application/json"
            }
        }
        return true
    }

    override fun postHandle(
        request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?
    ) {
        modelAndView?.takeIf { request.getHeader(X_INERTIA) != null }?.let {
            response.writer.write(it.model["page"] as String)
            it.clear()
        }
    }
}