import request from '@/utils/request'

const addResumeAPI = (data) => {
  return request.post('/resume/add', data)
}

const selectByPageAPI = (pageNum = 1, pageSize = 10, title) => {
  return request.get('/resume/selectPage', {
    params: {
      pageNum,
      pageSize,
      title
    }
  })
}

const selectResumeById = (id) => {
  return request.get('/resume/selectById', {
    params: {
      id
    }
  })
}

const selectAllResumeAPI = () => {
  return request.get('/resume/selectAll')
}

const updateResumeAPI = (data) => {
  return request.put('/resume/update', data)
}

const deleteById = (id) => {
  return request.delete('/resume/delete/' + id)
}

const deleteBatch = (ids) => {
  return request.delete('/resume/delete/batch', { data: ids })
}

export {
  addResumeAPI,
  selectByPageAPI,
  updateResumeAPI,
  deleteById,
  deleteBatch,
  selectAllResumeAPI,
  selectResumeById
}
