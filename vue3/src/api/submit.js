import request from '@/utils/request'

const addSubmitAPI = (data) => {
  return request.post('/submit/add', data)
}

const selectByPageAPI = (pageNum = 1, pageSize = 10, positionName, employId) => {
  return request.get('/submit/selectPage', {
    params: {
      pageNum,
      pageSize,
      positionName,
      employId
    }
  })
}

const selectAllSubmitAPI = (userId) => {
  return request.get('/submit/selectAll', { params: { userId } })
}

const updateSubmitAPI = (data) => {
  return request.put('/submit/update', data)
}

const deleteById = (id) => {
  return request.delete('/submit/delete/' + id)
}

const deleteBatch = (ids) => {
  return request.delete('/submit/delete/batch', { data: ids })
}

export {
  addSubmitAPI,
  selectByPageAPI,
  updateSubmitAPI,
  deleteById,
  deleteBatch,
  selectAllSubmitAPI
}
