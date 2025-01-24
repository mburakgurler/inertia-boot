import { createApp, h } from 'vue'
import { createInertiaApp } from '@inertiajs/vue3'
import Layout from './components/Layout.vue'
import './app.css'

createInertiaApp({
    resolve: async (name) => {
        const page = (await import(`./Pages/${name}.vue`)).default
        // page.layout = page.layout || Layout
        return page
    },
    setup({ el, App, props, plugin }) {
        createApp({ render: () => h(App, props) })
            .use(plugin)
            .mount(el)
    },
})