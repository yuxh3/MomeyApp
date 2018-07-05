package com.example.notwork.mvp.Contact

import android.content.Context
import android.util.Log
import com.example.notwork.`object`.HomeInfo
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.adapter.rxjava.HttpException

/**
 * Created by yuxh3
 * On 2018/4/11.
 * Email by 791285437@163.com
 */
abstract class HttpResultSubscriber<T>(context: Context,isShow:Boolean): Observer<HomeInfo<T>> {

    private val CODE_OK = "0"
    private var context:Context? = null

    private var errorMsg = "未知的错误"
    private var erroeCode = "-1"


    override fun onComplete() {
        Log.i("yuxh3","------------->onCompleted")
    }

    override fun onNext(t: HomeInfo<T>) {
        Log.i("yuxh3","------------->onNext")
        onSuccess(t.data)
    }

    override fun onError(e: Throwable) {
        Log.i("yuxh3","------------->onError")
        onFailue(e.toString())
    }

    override fun onSubscribe(d: Disposable) {

    }
    abstract fun onSuccess(data: T)//成功的操作

    abstract fun onFailue(errorMsg: String)//失败的操作


}