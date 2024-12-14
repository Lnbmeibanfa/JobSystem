import request from '@/utils/request'

const addIndustryAPI = (data) => {
  return request.post('/industry/add', data)
}

const selectByPageAPI = (pageNum = 1, pageSize = 10, name) => {
  return request.get('/industry/selectPage', {
    params: {
      pageNum,
      pageSize,
      name
    }
  })
}

const selectAllAPI = () => {
  return request.get('/industry/selectAll')
}

const updateIndustryAPI = (data) => {
  return request.put('/industry/update', data)
}

const deleteById = (id) => {
  return request.delete('/industry/delete/' + id)
}

const deleteBatch = (ids) => {
  return request.delete('/industry/delete/batch', { data: ids })
}

export { addIndustryAPI, selectByPageAPI, updateIndustryAPI, deleteById, deleteBatch, selectAllAPI }
