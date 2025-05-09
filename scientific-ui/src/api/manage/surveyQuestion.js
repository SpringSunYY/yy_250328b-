import request from '@/utils/request'

// 查询调研题目列表
export function listSurveyQuestion(query) {
  return request({
    url: '/manage/surveyQuestion/list',
    method: 'get',
    params: query
  })
}

export function questionList(query) {
  return request({
    url: '/manage/surveyQuestion/questionList',
    method: 'get',
    params: query
  })
}

// 查询调研题目详细
export function getSurveyQuestion(id) {
  return request({
    url: '/manage/surveyQuestion/' + id,
    method: 'get'
  })
}

// 新增调研题目
export function addSurveyQuestion(data) {
  return request({
    url: '/manage/surveyQuestion',
    method: 'post',
    data: data
  })
}

// 修改调研题目
export function updateSurveyQuestion(data) {
  return request({
    url: '/manage/surveyQuestion',
    method: 'put',
    data: data
  })
}

// 删除调研题目
export function delSurveyQuestion(id) {
  return request({
    url: '/manage/surveyQuestion/' + id,
    method: 'delete'
  })
}
