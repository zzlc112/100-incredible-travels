import request from './request'

export function createTravel(data) {
  return request.post('/admin/travels', data)
}

export function updateTravel(id, data) {
  return request.put(`/admin/travels/${id}`, data)
}

export function deleteTravel(id) {
  return request.delete(`/admin/travels/${id}`)
}
