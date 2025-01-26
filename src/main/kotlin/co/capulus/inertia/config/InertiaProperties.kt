package co.capulus.inertia.config
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "inertia")
class InertiaProperties {
    var version: String? = null
    var sharedData: MutableMap<String, Any> = mutableMapOf()
}