package co.capulus.inertia.controllers

import co.capulus.inertia.config.inertia
import co.capulus.inertia.models.User
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class HomeController(private val objectMapper: ObjectMapper) : BaseController() {

    @GetMapping("/")
    fun welcome(request: HttpServletRequest): ModelAndView {
        return inertia(
            component = "Welcome",
            props = mapOf(
                "canLogin" to false,
                "canRegister" to "false",
                "springVersion" to org.springframework.core.SpringVersion.getVersion(),
                "kotlinVersion" to KotlinVersion.CURRENT
            ),
            request = request,
            objectMapper = objectMapper
        )
    }

    @GetMapping("/home")
    fun index(request: HttpServletRequest): ModelAndView {
        val user = User(1, "dev vapulus", "dev@capulus.co")
        return inertia(
            component = "Home",
            props = mapOf(
                "user" to user,
                "title" to "Home Page"
            ),
            request = request,
            objectMapper = objectMapper
        )
    }

    @GetMapping("/about")
    fun about(request: HttpServletRequest): ModelAndView {
        return inertia(
            component = "About",
            props = mapOf("title" to "About Us"),
            request = request,
            objectMapper = objectMapper
        )
    }
}