import { isLegal } from '@/utils/ManagerUtil'

const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('用户名不得为空'))
  } else if (value.length < 5 || value.length > 50) {
    callback(new Error('用户名长度应为5-50'))
  } else if (!isLegal(value)) {
    callback(new Error('用户名中不得含有特殊字符'))
  } else {
    callback()
  }
}
const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('密码不得为空'))
  } else if (value.length < 6 || value.length > 20) {
    callback(new Error('密码为6-20位'))
  } else if (!isLegal(value)) {
    callback(new Error('密码中不得含有特殊字符'))
  } else {
    callback()
  }
}
const validatePhone = (rule, value, callback) => {
  const reg = /^1[0-9]\d{9}$/
  if (value === '') {
    callback(new Error('手机号不得为空'))
  } else if (reg.test(value)) {
    callback()
  } else {
    callback(new Error('手机号格式错误'))
  }
}
const validateEmail = (rule, value, callback) => {
  const reg = /^[0-9a-zA-z._+-]+@[a-zA-z0-9]+.[a-zA-z0-9-.]+$/
  if (value === '') {
    callback(new Error('邮箱不得为空'))
  } else if (reg.test(value)) {
    callback()
  } else {
    callback(new Error('邮箱格式错误'))
  }
}
export { validateUsername, validatePassword, validatePhone, validateEmail }
