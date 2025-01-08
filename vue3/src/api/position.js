import request from '@/utils/request'

const addPositionAPI = (data) => {
  return request.post('/position/add', data)
}

const selectByPageAPI = (pageNum = 1, pageSize = 10, name, employName) => {
  console.log(name, employName)
  return request.get('/position/selectPage', {
    params: {
      name,
      employName,
      pageNum,
      pageSize
    }
  })
}

const selectAllPositionAPI = () => {
  return request.get('/position/selectAll')
}

const selectAllPositionByIndustryAPI = (industryId) => {
  return request.get('/position/selectAll', {
    params: {
      industryId,
      status: '审核通过'
    }
  })
}

const selectAllPositionByEmployAPI = (employId, positionName) => {
  return request.get('/position/selectAll', {
    params: {
      employId,
      positionName
    }
  })
}

const selectById = (id) => {
  return request.get('position/selectById', {
    params: {
      id
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

const selectRecommend = () => {
  return request.get('/position/selectRecommend')
}

export {
  addPositionAPI,
  selectByPageAPI,
  updatePositionAPI,
  deleteById,
  deleteBatch,
  selectAllPositionAPI,
  selectAllPositionByIndustryAPI,
  selectAllPositionByEmployAPI,
  selectById,
  selectRecommend
}
