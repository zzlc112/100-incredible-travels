import axios from 'axios'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000,
  withCredentials: true
})

request.interceptors.response.use(
  (response) => {
    if (response.data.code === 200) {
      return response.data.data
    }
    return Promise.reject(response.data)
  },
  (error) => {
    return Promise.reject({ code: 500, message: '网络错误' })
  }
)

export default request
