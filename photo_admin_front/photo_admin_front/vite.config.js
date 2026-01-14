import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server: {
    //配置代理
    proxy: {
      //当请求的地址是 /api/..  例如：/api/login   代理请求成  http://localhost:8080/api/login
      '/api': {
        //代理的目标地址(后端api的ip和端口)
        target: 'http://localhost:8081',
        //允许跨域
        changeOrigin: true,
        //重写url地址，把/api 去掉 请求的就地址就变成了 http://localhost:8080/login
        //js的正则表达式 /正则匹配符/  /^\/api/   正斜杠 / 在正则中是特殊字符 用反斜杠转义 \/ 把正斜杠 变成普通字符
        rewrite: (path) => path.replace(/^\/api/, '')
      },
      //请求图片地址 http://localhost:8080/image/dsadasdada.jpg  所以代理路径不用重写
      '/image': {
        //代理的目标地址(后端api的ip和端口)
        target: 'http://localhost:8081',
        //允许跨域
        changeOrigin: true
      }
    }
  }
})
