import request from './request'

export function login(username, password) {
  return request.post('/admin/login', { username, password })
}

export function logout() {
  return request.post('/admin/logout')
}

export function checkAuth() {
  return request.get('/admin/check')
}
