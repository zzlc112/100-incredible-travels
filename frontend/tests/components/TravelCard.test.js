import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import TravelCard from '../../src/components/TravelCard.vue'
import ElementPlus from 'element-plus'

const mockTravel = {
  id: 1,
  title: 'Test Travel',
  subtitle: 'A great adventure',
  coverImage: 'https://example.com/img.jpg',
  experienceTypes: ['extreme', 'culture'],
  visualStyle: 'cinematic',
  rarityLevel: 3,
  destination: 'Iceland'
}

function mountCard(props = {}) {
  return mount(TravelCard, {
    props: { travel: mockTravel, ...props },
    global: { plugins: [ElementPlus] }
  })
}

describe('TravelCard', () => {
  it('renders title', () => {
    const wrapper = mountCard()
    expect(wrapper.text()).toContain('Test Travel')
  })

  it('renders destination', () => {
    const wrapper = mountCard()
    expect(wrapper.text()).toContain('Iceland')
  })

  it('renders rarity stars', () => {
    const wrapper = mountCard()
    expect(wrapper.text()).toContain('⭐⭐⭐')
  })

  it('emits click with travel id', async () => {
    const wrapper = mountCard()
    await wrapper.trigger('click')
    expect(wrapper.emitted('click')).toBeTruthy()
    expect(wrapper.emitted('click')[0]).toEqual([1])
  })

  it('shows placeholder on image error', async () => {
    const wrapper = mountCard()
    const img = wrapper.find('.card-cover img')
    await img.trigger('error')
    expect(wrapper.find('.cover-placeholder').exists()).toBe(true)
  })
})
