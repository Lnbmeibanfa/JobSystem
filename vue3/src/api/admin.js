import request from '@/utils/request'

const addAdminAPI = (data) => {
  return request.post('/admin/add', data)
}

const selectAllAPI = (pageNum = 1, pageSize = 10, name) => {
  return request.get('/admin/selectPage', {
    params: {
      pageNum,
      pageSize,
      name
    }
  })
}

export { addAdminAPI, selectAllAPI }
