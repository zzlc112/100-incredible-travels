<template>
  <div class="app-layout">
    <header class="app-header">
      <div class="header-inner">
        <router-link to="/" class="logo">100种不可思议旅行</router-link>
        <nav class="nav-links">
          <router-link to="/">首页</router-link>
        </nav>
      </div>
    </header>
    <main class="app-main">
      <slot />
    </main>
    <footer class="app-footer">
      <p>飞猪旅行旗下 · 100种不可思议旅行 &copy; 2026</p>
    </footer>
  </div>
</template>

<script setup>
</script>

<style scoped>
.app-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}
.app-header {
  position: sticky;
  top: 0;
  z-index: 100;
  background: rgba(26, 26, 46, 0.95);
  backdrop-filter: blur(10px);
  color: #fff;
  padding: 0 24px;
}
.header-inner {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
}
.logo {
  font-size: 20px;
  font-weight: 700;
  color: #fff;
  text-decoration: none;
}
.nav-links a {
  color: rgba(255,255,255,0.7);
  text-decoration: none;
  font-size: 14px;
  transition: color 0.2s;
}
.nav-links a:hover,
.nav-links a.router-link-active {
  color: #fff;
}
.app-main {
  flex: 1;
  max-width: 1200px;
  width: 100%;
  margin: 0 auto;
  padding: 24px;
}
.app-footer {
  text-align: center;
  padding: 32px 24px;
  color: #999;
  font-size: 13px;
}
</style>
