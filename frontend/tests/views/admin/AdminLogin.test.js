import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import AdminLogin from '../../../src/views/admin/AdminLogin.vue'
import ElementPlus from 'element-plus'

const mockLogin = vi.fn()
const mockPush = vi.fn()

vi.mock('@/api/auth', () => ({
  login: (...args) => mockLogin(...args)
}))

vi.mock('vue-router', () => ({
  useRouter: () => ({ push: mockPush })
}))

describe('AdminLogin', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('renders login form', () => {
    const wrapper = mount(AdminLogin, {
      global: { plugins: [ElementPlus] }
    })
    expect(wrapper.text()).toContain('后台管理')
    expect(wrapper.find('form').exists()).toBe(true)
  })

  it('shows validation errors on empty submit', async () => {
    const wrapper = mount(AdminLogin, {
      global: { plugins: [ElementPlus] }
    })
    await wrapper.find('form').trigger('submit')
    await wrapper.vm.$nextTick()
  })

  it('calls login on successful form submit', async () => {
    mockLogin.mockResolvedValue({})

    const wrapper = mount(AdminLogin, {
      global: { plugins: [ElementPlus] }
    })

    const inputs = wrapper.findAll('input')
    await inputs[0].setValue('admin')
    await inputs[1].setValue('admin123')

    mockLogin.mockResolvedValue({})
    await wrapper.find('form').trigger('submit')
    await wrapper.vm.$nextTick()
    await new Promise(r => setTimeout(r, 200))

    expect(mockLogin).toHaveBeenCalledWith('admin', 'admin123')
    expect(mockPush).toHaveBeenCalledWith('/admin/travels')
  })

  it('shows error on login failure', async () => {
    mockLogin.mockRejectedValue({ code: 401 })

    const wrapper = mount(AdminLogin, {
      global: { plugins: [ElementPlus] }
    })

    const inputs = wrapper.findAll('input')
    await inputs[0].setValue('admin')
    await inputs[1].setValue('wrong')

    await wrapper.find('form').trigger('submit')
    await wrapper.vm.$nextTick()
    await new Promise(r => setTimeout(r, 200))

    await wrapper.vm.$nextTick()
    expect(wrapper.find('.el-alert').exists()).toBe(true)
  })
})
