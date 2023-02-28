const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: "../backend/enrollment-sys/src/main/resources/static",
  devServer: {
    port: 4545,
    proxy: {
      '/api': {
        target: "http://127.0.0.1:8000",
        changeOrigin: true
      }
    }
  },
  pages: {
    home:{
        entry:'src/pages/home/main.js',
        template:'public/home.html',
        filename:'home.html'
    },
    admin: {
        entry:'src/pages/admin/main.js',
        template:'public/admin.html',
        filename:'admin.html'
    }
  }
})
