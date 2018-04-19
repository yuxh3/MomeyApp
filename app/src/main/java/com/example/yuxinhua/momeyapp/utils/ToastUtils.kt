package com.example.yuxinhua.momeyapp.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by yuxh3
 * On 2018/4/19.
 * Email by 791285437@163.com
 */
object ToastUtils{


    fun setShortMsg(context: Context,msg:String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }

    fun setLongMsg(context: Context,msg: String){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show()
    }
}