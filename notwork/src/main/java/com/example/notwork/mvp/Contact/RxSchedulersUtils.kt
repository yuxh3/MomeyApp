package com.example.notwork.mvp.Contact

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
/**
 * Created by yuxh3
 * On 2018/4/11.
 * Email by 791285437@163.com
 */
class RxSchedulersUtils{
    companion object {
        fun <T> io_main(): ObservableTransformer<T, T> {
            return ObservableTransformer {
                Observable -> Observable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }


        fun <T> compose(): ObservableTransformer<T, T> {
            return ObservableTransformer { observable ->
                observable.subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            }
        }
    }
}
