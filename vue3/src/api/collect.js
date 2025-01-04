import request from '@/utils/request'

const addCollectAPI = (data) => {
  return request.post('/collect/add', data)
}

const selectByPageAPI = (pageNum = 1, pageSize = 10, title) => {
  return request.get('/collect/selectPage', {
    params: {
      pageNum,
      pageSize,
      title
    }
  })
}

const selectAllCollectAPI = () => {
  return request.get('/collect/selectAll')
}

const updateCollectAPI = (data) => {
  return request.put('/collect/update', data)
}

const deleteById = (id) => {
  return request.delete('/collect/delete/' + id)
}

const deleteBatch = (ids) => {
  return request.delete('/collect/delete/batch', { data: ids })
}

export {
  addCollectAPI,
  selectByPageAPI,
  updateCollectAPI,
  deleteById,
  deleteBatch,
  selectAllCollectAPI
}
