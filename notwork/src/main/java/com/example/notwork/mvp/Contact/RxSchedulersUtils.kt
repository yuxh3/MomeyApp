package com.example.notwork.mvp.Contact

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by yuxh3
 * On 2018/4/11.
 * Email by 791285437@163.com
 */
class RxSchedulersUtils{
    companion object {
        fun <T> io_main(): Observable.Transformer<T,T>{
            return Observable.Transformer {
                Observable -> Observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }
}