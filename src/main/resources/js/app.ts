import { createApp, h } from 'vue'
import { createInertiaApp } from '@inertiajs/vue3'
import Layout from './components/Layout.vue'
import './app.css'

const appName = 'Kotlin';
const appMode = 'local';
const appDebug = 'false';

createInertiaApp({
    title: (title) => `${title} - ${appName}`,
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
    progress: {
        delay: 250,
        color: '#dd2298',
        includeCSS: true,
        showSpinner: false,
    },
}).then(() => {
    if (appMode !== 'local' && appDebug === 'false') {
        document.getElementById('app').removeAttribute('data-page');
    }
});