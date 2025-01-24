package co.capulus.inertia.config

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.servlet.ModelAndView

data class InertiaResponse(
    val component: String,
    val props: Map<String, Any?>,
    val url: String? = null,
    val version: String? = null
)

fun inertia(
    component: String,
    props: Map<String, Any?> = emptyMap(),
    request: HttpServletRequest,
    objectMapper: ObjectMapper
): ModelAndView {
    val data = mapOf(
        "component" to component,
        "props" to props,
        "url" to request.requestURI,
        "version" to null
    )
    val serializedData = objectMapper.writeValueAsString(data)

    return ModelAndView().apply {
        if (request.getHeader("X-Inertia") != null) {
            view = null
        } else {
            viewName = "app"
        }
        addObject("page", serializedData)
    }
}