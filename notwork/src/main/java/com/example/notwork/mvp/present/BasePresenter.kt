package com.example.notwork.mvp.present

/**
 * Created by yuxh3
 * On 2018/4/11.
 * Email by 791285437@163.com
 */
open class BasePresenter<T,E>{

    var mModel:E? = null
    var mView:T? = null

    fun setVM(t:T,e:E){

        this.mModel = e
        this.mView = t
    }
}