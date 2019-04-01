import Cookies from 'js-cookie';

const LoginKey = 'userToken';

export function getToken() {
  return Cookies.get(LoginKey);
}

export function setToken(data) {
  return Cookies.set(LoginKey, data);
}

export function removeToken() {
  return Cookies.remove(LoginKey);
}
