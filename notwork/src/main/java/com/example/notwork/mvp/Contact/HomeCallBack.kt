package com.example.notwork.mvp.Contact

/**
 * Created by yuxh3
 * On 2018/4/11.
 * Email by 791285437@163.com
 */
interface HomeCallBack<T>{

    fun success(data:T)
    fun error(code:Int,msg:String)
}