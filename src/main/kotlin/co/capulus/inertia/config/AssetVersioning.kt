package co.capulus.inertia.config

import jakarta.annotation.PostConstruct
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File
import java.security.MessageDigest

@Component
class AssetVersioning(private val inertiaProperties: InertiaProperties) {

    @PostConstruct
    @Scheduled(fixedRate = 5000)
    fun updateAssetVersion() {
        val assetPath = "src/main/resources/static/assets"
        val version = calculateAssetsHash(assetPath)
        inertiaProperties.version = version
    }

    private fun calculateAssetsHash(path: String): String {
        val directory = File(path)
        if (!directory.exists()) return "1"

        val files = directory.walkTopDown()
            .filter { it.isFile }
            .sortedBy { it.path }

        val md = MessageDigest.getInstance("MD5")

        files.forEach { file ->
            md.update(file.name.toByteArray())
            md.update(file.lastModified().toString().toByteArray())
        }

        return md.digest().joinToString("") { "%02x".format(it) }
    }
}