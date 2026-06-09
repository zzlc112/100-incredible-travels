import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import AdminEdit from '../../../src/views/admin/AdminEdit.vue'
import ElementPlus from 'element-plus'

const mockFetchDetail = vi.fn()
const mockCreate = vi.fn()
const mockUpdate = vi.fn()
const mockPush = vi.fn()
const mockBack = vi.fn()

vi.mock('@/api/travel', () => ({
  fetchTravelDetail: (...args) => mockFetchDetail(...args)
}))

vi.mock('@/api/admin-travel', () => ({
  createTravel: (...args) => mockCreate(...args),
  updateTravel: (...args) => mockUpdate(...args)
}))

vi.mock('vue-router', () => ({
  useRoute: () => ({ params: {} }),
  useRouter: () => ({ push: mockPush, back: mockBack })
}))

describe('AdminEdit - Create Mode', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('renders in create mode with empty form', () => {
    const wrapper = mount(AdminEdit, {
      global: { plugins: [ElementPlus] }
    })

    expect(wrapper.text()).toContain('新增旅行')
    expect(wrapper.find('form').exists()).toBe(true)
  })

  it('shows validation errors on empty submit', async () => {
    const wrapper = mount(AdminEdit, {
      global: { plugins: [ElementPlus] }
    })

    await wrapper.find('form').trigger('submit')
    await wrapper.vm.$nextTick()
  })

  it('can add and remove detail images', async () => {
    const wrapper = mount(AdminEdit, {
      global: { plugins: [ElementPlus] }
    })

    expect(wrapper.vm.form.detailImages.length).toBe(0)
    wrapper.vm.addDetailImage()
    await wrapper.vm.$nextTick()
    expect(wrapper.vm.form.detailImages.length).toBe(1)

    wrapper.vm.removeDetailImage(0)
    await wrapper.vm.$nextTick()
    expect(wrapper.vm.form.detailImages.length).toBe(0)
  })
})
