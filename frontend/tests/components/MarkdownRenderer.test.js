import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import MarkdownRenderer from '../../src/components/MarkdownRenderer.vue'

describe('MarkdownRenderer', () => {
  it('renders markdown as HTML', () => {
    const wrapper = mount(MarkdownRenderer, {
      props: { content: '# Hello World' }
    })
    expect(wrapper.find('.markdown-body').exists()).toBe(true)
    expect(wrapper.html()).toContain('<h1')
    expect(wrapper.text()).toContain('Hello World')
  })

  it('handles empty content', () => {
    const wrapper = mount(MarkdownRenderer, {
      props: { content: '' }
    })
    expect(wrapper.find('.markdown-body').exists()).toBe(true)
  })

  it('sets link target to _blank', () => {
    const wrapper = mount(MarkdownRenderer, {
      props: { content: '[link](https://example.com)' }
    })
    expect(wrapper.html()).toContain('target="_blank"')
  })
})
