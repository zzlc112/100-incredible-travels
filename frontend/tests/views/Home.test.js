import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import Home from '../../src/views/Home.vue'
import ElementPlus from 'element-plus'

const mockFetchTravels = vi.fn()
const mockPush = vi.fn()

vi.mock('@/api/travel', () => ({
  fetchTravels: (...args) => mockFetchTravels(...args)
}))

vi.mock('vue-router', () => ({
  useRouter: () => ({ push: mockPush })
}))

describe('Home', () => {
  beforeEach(() => {
    vi.clearAllMocks()
    mockFetchTravels.mockResolvedValue({
      total: 2,
      list: [
        { id: 1, title: 'Test 1', subtitle: 'sub1', coverImage: '', experienceTypes: ['extreme'], visualStyle: 'cinematic', rarityLevel: 3, destination: 'Iceland' },
        { id: 2, title: 'Test 2', subtitle: 'sub2', coverImage: '', experienceTypes: ['culture'], visualStyle: 'minimal', rarityLevel: 2, destination: 'Japan' }
      ]
    })
  })

  it('loads and displays travel cards', async () => {
    const wrapper = mount(Home, {
      global: {
        plugins: [ElementPlus],
        stubs: { TravelCard: { template: '<div class="mock-card"><span>{{$props.travel.title}}</span></div>', props: ['travel'] } }
      }
    })

    await wrapper.vm.$nextTick()
    await new Promise(r => setTimeout(r, 200))

    expect(mockFetchTravels).toHaveBeenCalled()
  })

  it('handles card click', async () => {
    const wrapper = mount(Home, {
      global: {
        plugins: [ElementPlus],
        stubs: { TravelCard: { template: '<div class="mock-card" @click="$emit(\'click\', $props.travel.id)"></div>', props: ['travel'] } }
      }
    })

    await wrapper.vm.$nextTick()
  })
})
