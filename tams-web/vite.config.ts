import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    port: 12011,
    proxy: {
      '/auth': {
        target: 'http://localhost:12010',
        changeOrigin: true
      },
      '/teacher': {
        target: 'http://localhost:12010',
        changeOrigin: true
      },
      '/course': {
        target: 'http://localhost:12010',
        changeOrigin: true
      },
      '/classroom': {
        target: 'http://localhost:12010',
        changeOrigin: true
      },
      '/course-scheduling': {
        target: 'http://localhost:12010',
        changeOrigin: true
      },
      '/clazz': {
        target: 'http://localhost:12010',
        changeOrigin: true
      },
      '/student': {
        target: 'http://localhost:12010',
        changeOrigin: true
      },
      '/color': {
        target: 'http://localhost:12010',
        changeOrigin: true
      },
      '/report': {
        target: 'http://localhost:12010',
        changeOrigin: true
      }
    }
  }
})

