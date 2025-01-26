package co.capulus.inertia.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode

@Component
class InertiaMiddleware(
    private val properties: InertiaProperties,
    private val objectMapper: ObjectMapper
) : HandlerInterceptor {

    companion object {
        private const val INERTIA_HEADER = "X-Inertia"
        private const val INERTIA_VERSION_HEADER = "X-Inertia-Version"
        private const val INERTIA_PARTIAL_DATA = "X-Inertia-Partial-Data"
        private const val INERTIA_PARTIAL_COMPONENT = "X-Inertia-Partial-Component"
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (isInertiaRequest(request)) {
            response.setHeader("Vary", "Accept")
            response.setHeader(INERTIA_HEADER, "true")

            val version = properties.version
            if (version != null && request.getHeader(INERTIA_VERSION_HEADER) != version) {
                if (request.method in listOf("PUT", "PATCH", "DELETE")) {
                    response.status = HttpStatus.CONFLICT.value()
                    return false
                }
            }

            request.setAttribute("partial-component", request.getHeader(INERTIA_PARTIAL_COMPONENT))
            request.setAttribute("partial-data", request.getHeader(INERTIA_PARTIAL_DATA)?.split(","))
        }
        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        if (modelAndView != null && isInertiaRequest(request)) {
            val page = modelAndView.model["page"] as String?
            if (page != null) {
                val pageData = objectMapper.readTree(page)
                val pageNode = objectMapper.createObjectNode()

                val component = pageData.get("component").asText()
                val partialComponent = request.getAttribute("partial-component") as String?
                val partialData = request.getAttribute("partial-data") as List<String>?
                val props = pageData.get("props") as ObjectNode

                val finalProps = if (partialComponent == component && partialData != null) {
                    objectMapper.createObjectNode().apply {
                        partialData.forEach { key ->
                            props.get(key)?.let { value -> set<ObjectNode>(key, value) }
                        }
                    }
                } else {
                    props
                }

                pageNode.put("component", component)
                pageNode.put("url", pageData.get("url").asText())
                pageNode.put("version", properties.version)
                pageNode.set<ObjectNode>("props", finalProps)

                properties.sharedData.forEach { (key, value) ->
                    (pageNode.get("props") as ObjectNode).set<ObjectNode>(key, objectMapper.valueToTree(value))
                }

                response.contentType = "application/json"
                response.writer.write(objectMapper.writeValueAsString(pageNode))
                modelAndView.clear()
            }
        }
    }

    private fun isInertiaRequest(request: HttpServletRequest): Boolean {
        return request.getHeader(INERTIA_HEADER) != null
    }
}