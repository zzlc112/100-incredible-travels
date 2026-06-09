import { describe, it, expect, beforeEach, vi } from 'vitest'

const mockCheckAuth = vi.fn()

vi.mock('@/api/auth', () => ({
  checkAuth: () => mockCheckAuth()
}))

describe('Router', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('should have Home as root route', async () => {
    const { default: router } = await import('@/router/index')

    const routes = router.getRoutes()
    const homeRoute = routes.find(r => r.path === '/')
    expect(homeRoute).toBeDefined()
  })

  it('should have admin routes with auth meta', async () => {
    const { default: router } = await import('@/router/index')

    const routes = router.getRoutes()
    const adminList = routes.find(r => r.path === '/admin/travels')
    expect(adminList).toBeDefined()
    expect(adminList.meta.auth).toBe(true)
  })

  it('should have guest meta on login route', async () => {
    const { default: router } = await import('@/router/index')

    const routes = router.getRoutes()
    const login = routes.find(r => r.path === '/admin/login')
    expect(login).toBeDefined()
    expect(login.meta.guest).toBe(true)
  })

  it('should have catch-all redirect to home', async () => {
    const { default: router } = await import('@/router/index')

    const routes = router.getRoutes()
    const catchAll = routes.find(r => r.path === '/:pathMatch(.*)*')
    expect(catchAll).toBeDefined()
  })
})
