import request from '@/utils/request'

// 查询用户调研列表
export function listSurveyUser(query) {
  return request({
    url: '/manage/surveyUser/list',
    method: 'get',
    params: query
  })
}

// 查询用户调研详细
export function getSurveyUser(id) {
  return request({
    url: '/manage/surveyUser/' + id,
    method: 'get'
  })
}

// 新增用户调研
export function addSurveyUser(data) {
  return request({
    url: '/manage/surveyUser',
    method: 'post',
    data: data
  })
}

// 修改用户调研
export function updateSurveyUser(data) {
  return request({
    url: '/manage/surveyUser',
    method: 'put',
    data: data
  })
}

// 删除用户调研
export function delSurveyUser(id) {
  return request({
    url: '/manage/surveyUser/' + id,
    method: 'delete'
  })
}
