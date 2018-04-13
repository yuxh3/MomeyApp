package com.example.notwork.Application

import android.app.Application

/**
 * Created by yuxh3
 * On 2018/4/11.
 * Email by 791285437@163.com
 */
class MApplication:Application(){

   var applicition:MApplication? = null

    override fun onCreate() {
        super.onCreate()
        applicition = this
    }
}