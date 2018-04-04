package com.example.yuxinhua.momeyapp.widgt

import android.os.Handler
import android.os.Looper
import android.os.Message

/**
 * Created by yuxh3
 * On 2018/4/3.
 * Email by 791285437@163.com
 */
class UIHandler : Handler {

    private var handler:IHandler?= null

    constructor(looper: Looper,handler:IHandler):super(looper){

        this.handler = handler
    }

    override fun handleMessage(msg: Message?) {
        super.handleMessage(msg)
        if (handler !== null){
            handler?.handleMessage(msg)
        }
    }

    interface IHandler {
        fun handleMessage(msg: Message?)
    }
}