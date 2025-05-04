import request from '@/utils/request'

// 查询提醒记录列表
export function listRemindHistory(query) {
  return request({
    url: '/manage/remindHistory/list',
    method: 'get',
    params: query
  })
}

// 查询提醒记录详细
export function getRemindHistory(id) {
  return request({
    url: '/manage/remindHistory/' + id,
    method: 'get'
  })
}

// 新增提醒记录
export function addRemindHistory(data) {
  return request({
    url: '/manage/remindHistory',
    method: 'post',
    data: data
  })
}

// 修改提醒记录
export function updateRemindHistory(data) {
  return request({
    url: '/manage/remindHistory',
    method: 'put',
    data: data
  })
}

// 删除提醒记录
export function delRemindHistory(id) {
  return request({
    url: '/manage/remindHistory/' + id,
    method: 'delete'
  })
}
