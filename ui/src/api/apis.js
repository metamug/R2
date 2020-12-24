import axios from 'axios'
import { API_URL } from 'constants/resource'

const apis = axios.create({
  baseURL: API_URL,
  headers: {
    Authorization: localStorage.token,
  },
})

const backendName = 'demo'

export async function doLogin(username, password) {
  return await apis.post(
    'accesstoken',
    `username=${username}&password=${password}`
  )
}

export async function fetchResources() {
  return await apis.get(`/app/${backendName}/rpx`)
}
export async function fetchXML(resourceName, resourceVersion) {
  return await apis.get(
    `/app/${localStorage.defaultItem}/rpx/${resourceVersion}/${resourceName}`
  )
}

export async function saveResourceXML(name, version, xmlBody) {
  return await apis.put(
    `/app/${localStorage.defaultItem}/rpx/${version}/${name}`,
    xmlBody
  )
}
export async function createNewResource(name, xmlBody) {
  return await apis.post(
    `/app/${localStorage.defaultItem}/rpx/`,
    `filename=${name}.xml&data=${encodeURIComponent(xmlBody)}`
  )
}

export async function deleteResource(name, version, type) {
  return await fetch(
    `${API_URL}/app/${localStorage.defaultItem}/rpx/${version}/${name}?type=${type}`,
    {
      headers: {
        Authorization: localStorage.token,
      },
      method: 'DELETE',
    }
  )
}
