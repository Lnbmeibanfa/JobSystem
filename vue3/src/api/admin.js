import request from '@/utils/request'

export function addAdminAPI(data) {
  console.log(data)
  return request.post('/admin/add', data)
}
