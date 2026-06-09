import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import AdminList from '../../../src/views/admin/AdminList.vue'
import ElementPlus from 'element-plus'

const mockFetchTravels = vi.fn()
const mockDeleteTravel = vi.fn()
const mockPush = vi.fn()

vi.mock('@/api/travel', () => ({
  fetchTravels: (...args) => mockFetchTravels(...args)
}))

vi.mock('@/api/admin-travel', () => ({
  deleteTravel: (...args) => mockDeleteTravel(...args)
}))

vi.mock('vue-router', () => ({
  useRouter: () => ({ push: mockPush })
}))

describe('AdminList', () => {
  beforeEach(() => {
    vi.clearAllMocks()
    mockFetchTravels.mockResolvedValue({
      total: 2,
      list: [
        { id: 1, title: 'Test 1', coverImage: '', experienceTypes: ['extreme'], visualStyle: 'cinematic', rarityLevel: 3, destination: 'Iceland', createdAt: '2026-01-01T00:00:00' },
        { id: 2, title: 'Test 2', coverImage: '', experienceTypes: ['culture'], visualStyle: 'minimal', rarityLevel: 2, destination: 'Japan', createdAt: '2026-01-02T00:00:00' }
      ]
    })
  })

  it('loads and renders list data', async () => {
    const wrapper = mount(AdminList, {
      global: { plugins: [ElementPlus] }
    })

    await wrapper.vm.$nextTick()
    await new Promise(r => setTimeout(r, 200))
    await wrapper.vm.$nextTick()

    expect(mockFetchTravels).toHaveBeenCalled()
  })

  it('navigates to create page on add button click', async () => {
    const wrapper = mount(AdminList, {
      global: { plugins: [ElementPlus] }
    })

    await wrapper.vm.$nextTick()
    await new Promise(r => setTimeout(r, 200))
    await wrapper.vm.$nextTick()

    const addBtn = wrapper.find('.el-button--primary')
    await addBtn.trigger('click')
    expect(mockPush).toHaveBeenCalledWith('/admin/travels/new')
  })
})
