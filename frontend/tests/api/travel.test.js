import { describe, it, expect, vi } from 'vitest'

const mockGet = vi.fn()
const mockPost = vi.fn()
const mockPut = vi.fn()
const mockDelete = vi.fn()

vi.mock('axios', () => ({
  default: {
    create: () => ({
      get: mockGet,
      post: mockPost,
      put: mockPut,
      delete: mockDelete,
      interceptors: {
        response: { use: vi.fn() }
      }
    })
  }
}))

describe('API Client', () => {
  it('fetchTravels should append params correctly', async () => {
    mockGet.mockResolvedValue({ data: { code: 200, data: { list: [], total: 0 } } })
    const { fetchTravels } = await import('../../src/api/travel')

    await fetchTravels({ page: 1, size: 10, experienceType: 'extreme' })

    expect(mockGet).toHaveBeenCalledWith('/travels', {
      params: { page: 1, size: 10, experienceType: 'extreme' }
    })
  })

  it('fetchTravelDetail should call correct URL', async () => {
    mockGet.mockResolvedValue({ data: { code: 200, data: { id: 1, title: 'Test' } } })
    const { fetchTravelDetail } = await import('../../src/api/travel')

    await fetchTravelDetail(1)
    expect(mockGet).toHaveBeenCalledWith('/travels/1')
  })

  it('checkAuth should call correct endpoint', async () => {
    mockGet.mockResolvedValue({ data: { code: 200, data: '已登录' } })
    const { checkAuth } = await import('../../src/api/auth')

    await checkAuth()
    expect(mockGet).toHaveBeenCalledWith('/admin/check')
  })
})
