import request from '@/utils/request'

const addEmployAPI = (data) => {
  return request.post('/employ/add', data)
}

const selectByPageAPI = (pageNum = 1, pageSize = 10, name, status) => {
  return request.get('/employ/selectPage', {
    params: {
      pageNum,
      pageSize,
      name,
      status
    }
  })
}

const updateEmployAPI = (data) => {
  return request.put('/employ/update', data)
}

const deleteById = (id) => {
  return request.delete('/employ/delete/' + id)
}

const deleteBatch = (ids) => {
  return request.delete('/employ/delete/batch', { data: ids })
}

const updatePasswordAPI = (data) => {
  return request.put('/employ/update/password', data)
}

export {
  addEmployAPI,
  selectByPageAPI,
  updateEmployAPI,
  deleteById,
  deleteBatch,
  updatePasswordAPI
}
