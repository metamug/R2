export async function userLoggedIn() {
  const status = await localStorage.getItem('token')
  debugger
  return !!status
}

export function encodePasswordBase64(password) {
  return btoa(password)
}
