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

const updateAdminAPI = (data) => {
  return request.put('/admin/update', data)
}

const deleteById = (id) => {
  return request.delete('/admin/delete/' + id)
}

const deleteBatch = (ids) => {
  return request.delete('/admin/delete/batch', { data: ids })
}

const updatePasswordAPI = (data) => {
  return request.put('/admin/update/password', data)
}

export { addAdminAPI, selectAllAPI, updateAdminAPI, deleteById, deleteBatch, updatePasswordAPI }
