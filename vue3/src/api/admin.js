import request from '@/utils/request'

export function addAdminAPI(data) {
  return request.post('/admin/add', data)
}
