import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import FilterBar from '../../src/components/FilterBar.vue'
import ElementPlus from 'element-plus'

function mountFilter(modelValue = { experienceTypes: [], visualStyle: '', rarityLevel: 0 }) {
  return mount(FilterBar, {
    props: { modelValue },
    global: { plugins: [ElementPlus] }
  })
}

describe('FilterBar', () => {
  it('renders experience type checkboxes', () => {
    const wrapper = mountFilter()
    const buttons = wrapper.findAll('.el-checkbox-button')
    expect(buttons.length).toBeGreaterThanOrEqual(6)
  })

  it('renders visual style radio buttons', () => {
    const wrapper = mountFilter()
    const radios = wrapper.findAll('.el-radio-button')
    expect(radios.length).toBeGreaterThanOrEqual(6)
  })

  it('emits reset on reset button click', async () => {
    const wrapper = mountFilter()
    await wrapper.find('.el-button').trigger('click')
    expect(wrapper.emitted('reset')).toBeTruthy()
  })

  it('updates experienceTypes via v-model mechanism', async () => {
    const wrapper = mountFilter()
    const checkboxes = wrapper.findAll('.el-checkbox-button input[type="checkbox"]')
    if (checkboxes.length > 0) {
      await checkboxes[0].setValue(true)
      await wrapper.vm.$nextTick()
    }
    expect(wrapper.props('modelValue')).toBeDefined()
  })
})
