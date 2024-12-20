import request from '@/utils/request'
const loginAPI = (data) => {
  return request.post('/login', data)
}

const registerAPI = (data) => {
  return request.post('/register', data)
}

const updatePasswordAPI = (data) => {
  return request.put('/update/password', data)
}
export { loginAPI, registerAPI, updatePasswordAPI }
