package co.capulus.inertia.config

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.servlet.ModelAndView

data class InertiaResponse<T>(
    val component: String,
    val props: T,
    val url: String? = null,
    val version: String? = null
)

fun <T> inertia(
    component: String,
    props: T,
    request: HttpServletRequest,
    objectMapper: ObjectMapper
): ModelAndView {
    val data = InertiaResponse(
        component = component,
        props = props,
        url = request.requestURI
    )

    val serializedData = objectMapper.writeValueAsString(data)

    return if (request.getHeader("X-Inertia") != null) {
        ModelAndView().apply {
            view = null
            addObject("page", serializedData)
        }
    } else {
        ModelAndView("app").apply {
            viewName = "app"
            addObject("page", serializedData)
        }
    }
}