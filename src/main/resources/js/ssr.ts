import { createSSRApp, h } from 'vue';
import { renderToString } from '@vue/server-renderer';
import { createInertiaApp } from '@inertiajs/vue3';
import createServer from '@inertiajs/vue3/server';

const appName = 'Kotlin';
const appMode = 'local';
const appDebug = 'false';

createServer((page) =>
    createInertiaApp({
        page,
        render: renderToString,
        title: (title) => `${title} - ${appName}`,
        resolve: async (name) => {
            const pages = import.meta.glob('./Pages/**/*.vue')
            const page = pages[`./Pages/${name}.vue`]
            return (await page()).default
        },
        setup({ App, props, plugin }) {
            return createSSRApp({ render: () => h(App, props) })
                .use(plugin);
        },
    }).then((app) => {
        if (appMode !== 'local' && appDebug === 'false') {
            const appRender = app;
            appRender.body = appRender.body.replace(/data-page=".*?"/, '');
            return appRender;
        }

    })
);