const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: "../backend/enrollment-sys/src/main/resources/static",
  devServer: {
    port: 4545,
    proxy: "http://localhost:8080"
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
