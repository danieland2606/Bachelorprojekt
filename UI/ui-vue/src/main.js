import { createApp } from 'vue/dist/vue.esm-bundler';
import { createRouter, createWebHashHistory } from 'vue-router';
import './style.css';
import Dashboard from './components/Dashboard.vue';

const routes = [
    { path: '/', component: Dashboard }
];

const router = createRouter({history: createWebHashHistory(), routes});

const app = createApp({});
app.use(router);
app.mount('#app');
