import request from '@/utils/request'

// 查询用户填写的完整问卷记录列表
export function listSurveyAnswer(query) {
  return request({
    url: '/manage/surveyAnswer/list',
    method: 'get',
    params: query
  })
}

// 查询用户填写的完整问卷记录详细
export function getSurveyAnswer(id) {
  return request({
    url: '/manage/surveyAnswer/' + id,
    method: 'get'
  })
}

// 新增用户填写的完整问卷记录
export function addSurveyAnswer(data) {
  return request({
    url: '/manage/surveyAnswer',
    method: 'post',
    data: data
  })
}

export function addByAnswerList(data) {
  return request({
    url: '/manage/surveyAnswer/add/batch',
    method: 'post',
    data: data
  })
}

// 修改用户填写的完整问卷记录
export function updateSurveyAnswer(data) {
  return request({
    url: '/manage/surveyAnswer',
    method: 'put',
    data: data
  })
}

// 删除用户填写的完整问卷记录
export function delSurveyAnswer(id) {
  return request({
    url: '/manage/surveyAnswer/' + id,
    method: 'delete'
  })
}
