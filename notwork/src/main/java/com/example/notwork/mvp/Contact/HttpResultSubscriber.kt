package com.example.notwork.mvp.Contact

import android.content.Context
import com.example.notwork.`object`.HomeInfo
import retrofit2.adapter.rxjava.HttpException
import rx.Subscriber

/**
 * Created by yuxh3
 * On 2018/4/11.
 * Email by 791285437@163.com
 */
abstract class HttpResultSubscriber<T>():Subscriber<HomeInfo<T>>(){

    private val CODE_OK = "0"
    private var context:Context? = null

    private var errorMsg = "未知的错误"
    private var erroeCode = "-1"


    constructor(context: Context,isShow:Boolean):this(){
        this.context = context

        if (isShow){
            //
        }
    }

    override fun onCompleted() {

    }

    override fun onError(e: Throwable?) {
        e?.printStackTrace()
        if (e is HttpException){
            onFailue((e as HttpException).code(),errorMsg)
        }
    }

    override fun onNext(t: HomeInfo<T>?) {
        if (t?.code == CODE_OK){
            onSuccess(t?.data)
        }else{
            onFailue(t!!.code.toInt(),t.message)
        }
    }

    abstract fun onSuccess(data: ArrayList<T>?)//成功的操作

    abstract fun onFailue(code: Int, errorMsg: String)//失败的操作


}