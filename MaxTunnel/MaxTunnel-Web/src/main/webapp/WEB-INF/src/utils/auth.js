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
let session ={
    key:'userToken',
    getSession(){
        return sessionStorage.getItem(this.key);
    },
    setSession(value){
        if( typeof value !== 'string') {
            value = JSON.stringify(value);
        }
        return sessionStorage.setItem(this.key,value)
    },
    removeSession(){
        return sessionStorage.removeItem(this.key)
    },
    clearSession(){
        return sessionStorage.clear()
    }
}

export default session;