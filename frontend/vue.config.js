const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: "../backend/enrollment-sys/src/main/resources/static",
  devServer: {
    port: 4545,
    proxy: "http://localhost:8080"
  }
})
