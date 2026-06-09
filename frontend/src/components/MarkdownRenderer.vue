<template>
  <div class="markdown-body" v-html="renderedHtml"></div>
</template>

<script setup>
import { computed } from 'vue'
import { marked } from 'marked'
import 'github-markdown-css/github-markdown.css'

const props = defineProps({
  content: { type: String, required: true }
})

marked.setOptions({
  breaks: true
})

const renderer = new marked.Renderer()
renderer.link = function ({ href, title, text }) {
  return `<a href="${href}" target="_blank" rel="noopener" title="${title || ''}">${text}</a>`
}

const renderedHtml = computed(() => {
  return marked.parse(props.content || '', { renderer })
})
</script>

<style scoped>
.markdown-body {
  box-sizing: border-box;
  min-width: 200px;
  max-width: 100%;
  padding: 24px 0;
}
</style>
