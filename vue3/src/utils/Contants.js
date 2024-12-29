// 路由定义常量
const ROUTE_PATH = {
  HOME: '/manager/home',
  MESSAGE: {
    INDUSTRY: '/manager/industry',
    NOTICE: '/manager/notice',
    POSITION: '/manager/position',
    ADVERTISE: '/manager/advertise'
  },
  ACCOUNT: {
    ADMIN: '/manager/admin',
    SELF: '/manager/self',
    PASSWORD: '/manager/password',
    EMPLOY: '/manager/employ',
    USER: '/manager/user',
    EMPLOYSELF: '/manager/eSelf'
  },
  INVALID: '/404',
  LOGIN: '/login',
  REGISTER: '/register',
  FRONT: '/front',
  MANAGER: '/manager'
}
// 用户角色
const ROLE = {
  ADMIN: '管理员',
  EMPLOY: '企业',
  USER: '用户'
}
// 审核状态
const STATUS = {
  AUDIT_STATUS_PENDING: '待审核',
  AUDIT_STATUS_APPROVED: '审核通过',
  AUDIT_STATUS_REJECTED: '审核不通过'
}
// localstorage的key
const LOCALSTORAGE_KEY = {
  USER: 'pinia-userInfo'
}
// 枚举类型
const OPTIONS = {
  EMPLOY: {
    SCALE: [
      { label: '0-20人', value: '0-20人' },
      { label: '21-99人', value: '21-99人' },
      { label: '100-499人', value: '100-499人' },
      { label: '500-999人', value: '500-999人' },
      { label: '1000-9999人', value: '1000-9999人' },
      { label: '10000人以上', value: '10000人以上' }
    ],
    STAGE: [
      { label: '未融资', value: '未融资' },
      { label: '天使轮', value: '天使轮' },
      { label: 'A轮', value: 'A轮' },
      { label: 'B轮', value: 'B轮' },
      { label: 'C轮', value: 'C轮' },
      { label: 'D轮', value: 'D轮' },
      { label: '已上市', value: '已上市' },
      { label: '不需要融资', value: '不需要融资' }
    ],
    STATUS: [
      { label: '待审核', value: '待审核' },
      { label: '审核通过', value: '审核通过' },
      { label: '审核不通过', value: '审核不通过' }
    ]
  }
}
export { ROUTE_PATH, ROLE, LOCALSTORAGE_KEY, OPTIONS, STATUS }
