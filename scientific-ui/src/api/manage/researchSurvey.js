import request from '@/utils/request'

// 查询调研信息列表
export function listResearchSurvey(query) {
  return request({
    url: '/manage/researchSurvey/list',
    method: 'get',
    params: query
  })
}

// 查询调研信息详细
export function getResearchSurvey(id) {
  return request({
    url: '/manage/researchSurvey/' + id,
    method: 'get'
  })
}

// 新增调研信息
export function addResearchSurvey(data) {
  return request({
    url: '/manage/researchSurvey',
    method: 'post',
    data: data
  })
}

// 修改调研信息
export function updateResearchSurvey(data) {
  return request({
    url: '/manage/researchSurvey',
    method: 'put',
    data: data
  })
}

// 删除调研信息
export function delResearchSurvey(id) {
  return request({
    url: '/manage/researchSurvey/' + id,
    method: 'delete'
  })
}
