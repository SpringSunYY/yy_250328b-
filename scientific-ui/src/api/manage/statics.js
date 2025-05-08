import request from '@/utils/request'

export function loginCount(query) {
  return request({
    url: '/manage/statics/loginCount',
    method: 'get',
    params: query
  })
}

export function userCount(query) {
  return request({
    url: '/manage/statics/userCount',
    method: 'get',
    params: query
  })
}

export function loginCountDay(query) {
  return request({
    url: '/manage/statics/loginCountDay',
    method: 'get',
    params: query
  })
}

export function userSexPie(query) {
  return request({
    url: '/manage/statics/userSexPie',
    method: 'get',
    params: query
  })
}

export function userAge(query) {
  return request({
    url: '/manage/statics/userAge',
    method: 'get',
    params: query
  })
}
