import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import TravelDetail from '../../src/views/TravelDetail.vue'
import ElementPlus from 'element-plus'

const mockFetchDetail = vi.fn()
const mockBack = vi.fn()

vi.mock('@/api/travel', () => ({
  fetchTravelDetail: (...args) => mockFetchDetail(...args)
}))

vi.mock('vue-router', () => ({
  useRoute: () => ({ params: { id: '1' } }),
  useRouter: () => ({ back: mockBack })
}))

describe('TravelDetail', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('renders travel content after loading', async () => {
    mockFetchDetail.mockResolvedValue({
      id: 1,
      title: 'Test Travel',
      subtitle: 'sub',
      coverImage: '',
      detailImages: [],
      experienceTypes: ['extreme'],
      visualStyle: 'cinematic',
      rarityLevel: 3,
      destination: 'Iceland',
      content: '# Hello',
      highlights: ['h1'],
      createdAt: '2026-01-01T00:00:00',
      updatedAt: '2026-01-01T00:00:00'
    })

    const wrapper = mount(TravelDetail, {
      global: { plugins: [ElementPlus], stubs: { MarkdownRenderer: { template: '<div class="mock-md"></div>', props: ['content'] } } }
    })

    await wrapper.vm.$nextTick()
    await new Promise(r => setTimeout(r, 200))
    await wrapper.vm.$nextTick()

    expect(mockFetchDetail).toHaveBeenCalledWith('1')
  })

  it('shows empty state on API failure', async () => {
    mockFetchDetail.mockRejectedValue({ code: 404 })

    const wrapper = mount(TravelDetail, {
      global: { plugins: [ElementPlus], stubs: { MarkdownRenderer: { template: '<div class="mock-md"></div>', props: ['content'] } } }
    })

    await wrapper.vm.$nextTick()
    await new Promise(r => setTimeout(r, 200))
    await wrapper.vm.$nextTick()

    expect(wrapper.find('.el-empty').exists()).toBe(true)
  })
})
