import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import ImagePreview from '../../src/components/ImagePreview.vue'
import ElementPlus from 'element-plus'

describe('ImagePreview', () => {
  it('renders image with given URL', () => {
    const wrapper = mount(ImagePreview, {
      props: { url: 'https://example.com/img.jpg' },
      global: { plugins: [ElementPlus] }
    })
    expect(wrapper.find('img').exists()).toBe(true)
  })

  it('shows placeholder on image error', async () => {
    const wrapper = mount(ImagePreview, {
      props: { url: 'https://example.com/img.jpg' },
      global: { plugins: [ElementPlus] }
    })
    await wrapper.find('img').trigger('error')
    expect(wrapper.find('.image-placeholder').exists()).toBe(true)
  })
})
