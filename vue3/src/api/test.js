import request from "@/utils/request";

export function testRequest() {
  return request({
    url: '/test'
  })
}