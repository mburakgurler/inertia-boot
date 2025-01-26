package co.capulus.inertia.controllers
import co.capulus.inertia.config.InertiaProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.ModelAttribute

abstract class BaseController {
    @Autowired
    private lateinit var inertiaProperties: InertiaProperties

    @ModelAttribute
    fun share() {
        inertiaProperties.sharedData["auth"] = mapOf(
            "user" to null
        )

        inertiaProperties.sharedData["flash"] = mapOf(
            "success" to getFlashMessage("success"),
            "error" to getFlashMessage("error")
        )

        inertiaProperties.sharedData["errors"] = getValidationErrors()
    }

    private fun getFlashMessage(key: String): String? {
        return null
    }

    private fun getValidationErrors(): Map<String, String> {
        return emptyMap()
    }
}