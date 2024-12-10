import request from '@/utils/request'
const loginAPI = (data) => {
  return request.post('/login', data)
}

const registerAPI = (data) => {
  return request.post('register', data)
}

export { loginAPI, registerAPI }
