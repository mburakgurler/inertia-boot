import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'url'

export default defineConfig(({ command, mode, ssrBuild }) => {
    const config = {
        plugins: [vue()],
        root: './src/main/resources',
        base: '/',
        server: {
            port: 3000,
            strictPort: true
        },
        resolve: {
            alias: {
                '@': fileURLToPath(new URL('./src/main/resources/js', import.meta.url))
            }
        }
    }

    const buildConfig = ssrBuild ? {
        ssr: true,
        outDir: 'static/assets/ssr',
        rollupOptions: {
            input: './src/main/resources/js/ssr.ts',
            external: [
                'http',
                'process',
                '@vue/server-renderer',
                '@inertiajs/core/server'
            ]
        }
    } : {
        outDir: 'static/assets',
        assetsDir: '',
        manifest: true,
        rollupOptions: {
            input: './src/main/resources/js/app.ts'
        }
    }

    return { ...config, build: buildConfig }
})