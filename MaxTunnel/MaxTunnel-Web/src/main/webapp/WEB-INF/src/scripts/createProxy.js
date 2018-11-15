import Vue from 'vue'

/**
 *  对象监听
 *
 *  监听对象中的对象,数组,字符串,数字类型 变化
 *  data : 监听对象
 *  fun : 触发变化的回调
 */

const createProxy = ( data,fun ) => {
    if ( typeof data === 'object' && data.toString() === '[object Object]' ) {
        for ( let k in data ) {
            if ( typeof data[k] === 'object' ) {
                defineObjectReactive( data, k, data[k],fun )
            } else {
                defineBasicReactive( data, k, data[k],fun )
            }
        }
    }
}

function defineObjectReactive( obj, key, value,callback ) {
    // 递归
    createProxy( value,callback );
    obj[key] = new Proxy(value, {
        set(target, property, val, receiver) {
            if (property !== 'length') {
                callback.call( this,val );
            }
            return Reflect.set(target, property, val, receiver)
        }
    })
}

function defineBasicReactive( obj, key, value,callback ) {
    Object.defineProperty(obj, key, {
        enumerable: true,
        configurable: false,
        get() {
            return value
        },
        set( newValue ) {
            if (value === newValue) return
            callback.call( this,value )
            value = newValue
        }
    })
}

export default createProxy;