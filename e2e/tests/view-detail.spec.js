const { test, expect } = require('@playwright/test');

test.describe('View Travel Detail', () => {
  test('E2E-02: Navigate to detail from home page', async ({ page }) => {
    await page.goto('/');
    await page.waitForLoadState('networkidle');
    await page.waitForSelector('.travel-card', { timeout: 10000 });

    // Click the first travel card
    const firstCard = page.locator('.travel-card').first();
    await firstCard.click();
    await page.waitForLoadState('networkidle');

    // Verify detail page loaded
    await expect(page.locator('.detail-title')).toBeVisible();
    await expect(page.locator('.markdown-body')).toBeVisible();
  });

  test('E2E-02b: Go back from detail page', async ({ page }) => {
    await page.goto('/');
    await page.waitForLoadState('networkidle');
    await page.waitForSelector('.travel-card', { timeout: 10000 });

    const firstCard = page.locator('.travel-card').first();
    await firstCard.click();
    await page.waitForLoadState('networkidle');

    // Click back button
    const backBtn = page.locator('.el-page-header__back');
    if (await backBtn.isVisible()) {
      await backBtn.click();
    }
    await page.waitForTimeout(500);
  });
});
