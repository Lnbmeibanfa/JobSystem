import request from '@/utils/request'

const selectAllCountsAPI = () => {
  return request.get('/statistics/base')
}

const selectPieAPI = () => {
  return request.get('/statistics/pie')
}

const selectBarAPI = () => {
  return request.get('/statistics/bar')
}

const selectLineAPI = () => {
  return request.get('/statistics/line')
}

export { selectAllCountsAPI, selectPieAPI, selectBarAPI, selectLineAPI }
