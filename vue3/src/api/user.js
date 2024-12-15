import request from '@/utils/request'

const addUserAPI = (data) => {
  return request.post('/user/add', data)
}

const selectByPageAPI = (pageNum = 1, pageSize = 10, name) => {
  return request.get('/user/selectPage', {
    params: {
      pageNum,
      pageSize,
      name
    }
  })
}

const updateUserAPI = (data) => {
  return request.put('/user/update', data)
}

const deleteById = (id) => {
  return request.delete('/user/delete/' + id)
}

const deleteBatch = (ids) => {
  return request.delete('/user/delete/batch', { data: ids })
}

const updatePasswordAPI = (data) => {
  return request.put('/user/update/password', data)
}

export { addUserAPI, selectByPageAPI, updateUserAPI, deleteById, deleteBatch, updatePasswordAPI }
