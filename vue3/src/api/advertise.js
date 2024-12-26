import request from '@/utils/request'

const addAdvertiseAPI = (data) => {
  return request.post('/advertise/add', data)
}

const selectByPageAPI = (pageNum = 1, pageSize = 10, location) => {
  return request.get('/advertise/selectPage', {
    params: {
      pageNum,
      pageSize,
      location
    }
  })
}

const selectById = (id) => {
  return request.get('/advertise/selectById', {
    params: {
      id
    }
  })
}

const updateAdvertiseAPI = (data) => {
  return request.put('/advertise/update', data)
}

const deleteById = (id) => {
  return request.delete('/advertise/delete/' + id)
}

const deleteBatch = (ids) => {
  return request.delete('/advertise/delete/batch', { data: ids })
}

const selectAllAdvertiseAPI = () => {
  return request.get('/advertise/selectAll')
}

export {
  addAdvertiseAPI,
  selectByPageAPI,
  updateAdvertiseAPI,
  deleteById,
  deleteBatch,
  selectById,
  selectAllAdvertiseAPI
}
