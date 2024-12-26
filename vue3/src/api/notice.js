import request from '@/utils/request'

const addNoticeAPI = (data) => {
  return request.post('/notice/add', data)
}

const selectByPageAPI = (pageNum = 1, pageSize = 10, title) => {
  return request.get('/notice/selectPage', {
    params: {
      pageNum,
      pageSize,
      title
    }
  })
}

const selectAllNoticeAPI = () => {
  return request.get('/notice/selectAll')
}

const updateNoticeAPI = (data) => {
  return request.put('/notice/update', data)
}

const deleteById = (id) => {
  return request.delete('/notice/delete/' + id)
}

const deleteBatch = (ids) => {
  return request.delete('/notice/delete/batch', { data: ids })
}

export {
  addNoticeAPI,
  selectByPageAPI,
  updateNoticeAPI,
  deleteById,
  deleteBatch,
  selectAllNoticeAPI
}
