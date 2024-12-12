// 路由定义常量
const ROUTE_PATH = {
  HOME: '/manager/home',
  ADMIN: '/manager/admin',
  USER: '/manager/user',
  PASSWORD: '/manager/password',
  INVALID: '/404',
  LOGIN: '/login',
  REGISTER: '/register',
  FRONT: '/front'
}

const ROLE = {
  ADMIN: '管理员'
}

const LOCALSTORAGE_KEY = {
  USER: 'pinia-userInfo'
}

export { ROUTE_PATH, ROLE, LOCALSTORAGE_KEY }
