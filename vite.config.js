// vite.config.js
import { defineConfig } from 'vite';

export default defineConfig({
    define: {
        __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: false,
        __VUE_OPTIONS_API__: true,     // 可选
        __VUE_PROD_DEVTOOLS__: false   // 可选
    }
});