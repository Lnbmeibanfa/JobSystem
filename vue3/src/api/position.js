import request from '@/utils/request'

const addPositionAPI = (data) => {
  return request.post('/position/add', data)
}

const selectByPageAPI = (pageNum = 1, pageSize = 10, name, employName) => {
  return request.get('/position/selectPage', {
    params: {
      pageNum,
      pageSize,
      name,
      employName
    }
  })
}

const selectAllPositionAPI = (industryId) => {
  return request.get('/position/selectAll', {
    params: {
      industryId
    }
  })
}

const updatePositionAPI = (data) => {
  return request.put('/position/update', data)
}

const deleteById = (id) => {
  return request.delete('/position/delete/' + id)
}

const deleteBatch = (ids) => {
  return request.delete('/position/delete/batch', { data: ids })
}

export {
  addPositionAPI,
  selectByPageAPI,
  updatePositionAPI,
  deleteById,
  deleteBatch,
  selectAllPositionAPI
}