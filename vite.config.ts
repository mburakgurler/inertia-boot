import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'url'

export default defineConfig({
    plugins: [vue()],
    root: './src/main/resources',
    base: '/',
    server: {
        port: 3000,
        strictPort: true,
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                changeOrigin: true
            }
        }
    },
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src/main/resources/js', import.meta.url))
        }
    },
    build: {
        outDir: 'static/assets',
        assetsDir: '',
        manifest: true
    }
})