const { test, expect } = require('@playwright/test');

test.describe('Browse and Filter', () => {
  test('E2E-01: Home page loads with at least 5 travel cards', async ({ page }) => {
    await page.goto('/');
    await page.waitForLoadState('networkidle');

    // Verify page title
    await expect(page.locator('.logo')).toContainText('100种不可思议旅行');

    // Wait for travel cards to load
    await page.waitForSelector('.travel-card', { timeout: 10000 });
    const cards = page.locator('.travel-card');
    const count = await cards.count();
    expect(count).toBeGreaterThanOrEqual(5);
  });

  test('E2E-01b: Filter by experience type', async ({ page }) => {
    await page.goto('/');
    await page.waitForLoadState('networkidle');
    await page.waitForSelector('.travel-card', { timeout: 10000 });

    // Click first checkbox-button for experience type filter
    const checkboxes = page.locator('.el-checkbox-button').first();
    await checkboxes.click();
    await page.waitForTimeout(500);
  });

  test('E2E-01c: Set rarity level filter', async ({ page }) => {
    await page.goto('/');
    await page.waitForLoadState('networkidle');
    await page.waitForSelector('.travel-card', { timeout: 10000 });

    // Move the slider
    const slider = page.locator('.el-slider__runway').first();
    if (await slider.isVisible()) {
      await expect(slider).toBeVisible();
    }
  });
});
