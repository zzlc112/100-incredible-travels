const { defineConfig } = require('@playwright/test');

module.exports = defineConfig({
  testDir: './tests',
  timeout: 30000,
  retries: 1,
  use: {
    baseURL: 'http://localhost:5173',
    headless: true,
    screenshot: 'only-on-failure'
  },
  webServer: [
    {
      command: 'cd ../backend && mvn spring-boot:run',
      port: 8080,
      timeout: 120000,
      reuseExistingServer: true
    },
    {
      command: 'cd ../frontend && npm run dev',
      port: 5173,
      timeout: 30000,
      reuseExistingServer: true
    }
  ]
});
