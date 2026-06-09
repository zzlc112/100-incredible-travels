const { test, expect } = require('@playwright/test');

test.describe('Admin CRUD Flow', () => {
  test('E2E-03: Login and create travel content', async ({ page }) => {
    // Navigate to admin login
    await page.goto('/admin/login');
    await page.waitForLoadState('networkidle');

    // Fill login form
    const inputs = page.locator('input');
    await inputs.first().fill('admin');
    await inputs.nth(1).fill('admin123');

    // Submit login
    await page.locator('button[type="submit"]').click();
    await page.waitForURL('**/admin/travels');
    await page.waitForLoadState('networkidle');

    // Click add button
    const addBtn = page.locator('.el-button--primary').filter({ hasText: '新增' });
    await addBtn.click();
    await page.waitForURL('**/admin/travels/new');

    // Verify form is visible
    await expect(page.locator('.edit-form')).toBeVisible();
    await expect(page.locator('h2')).toContainText('新增旅行');
  });

  test('E2E-03b: Admin login with wrong credentials', async ({ page }) => {
    await page.goto('/admin/login');
    await page.waitForLoadState('networkidle');

    const inputs = page.locator('input');
    await inputs.first().fill('wrong');
    await inputs.nth(1).fill('password');

    await page.locator('button[type="submit"]').click();
    await page.waitForTimeout(500);

    // Error message should appear
    await expect(page.locator('.el-alert--error')).toBeVisible();
  });
});
