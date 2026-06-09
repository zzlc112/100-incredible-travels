import request from './request'

export function fetchTravels(params) {
  return request.get('/travels', { params })
}

export function fetchTravelDetail(id) {
  return request.get(`/travels/${id}`)
}
