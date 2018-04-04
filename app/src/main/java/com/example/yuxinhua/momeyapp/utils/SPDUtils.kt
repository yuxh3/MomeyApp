package com.example.yuxinhua.momeyapp.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by yuxh3
 * On 2018/3/28.
 * Email by 791285437@163.com
 */
class SPDUtils(context: Context) {

    var mSpdUtils: SharedPreferences? = null
    init {
        mSpdUtils = context.getSharedPreferences("Data",Context.MODE_PRIVATE)
    }

    fun setString(msg:String,ist:String){
        mSpdUtils?.edit()?.putString(msg,ist)?.commit()
    }
    fun getString(msg:String):String?{
        return mSpdUtils?.getString(msg,"")
    }

    fun setInt(msg:String,ist:Int){
        mSpdUtils?.edit()?.putInt(msg,ist)?.commit()
    }
    fun getInt(msg:String):Int?{
        return mSpdUtils?.getInt(msg,0)
    }

    fun setBoolean(msg:String,ist:Boolean){
        mSpdUtils?.edit()?.putBoolean(msg,ist)?.commit()
    }
    fun getBoolean(msg:String):Boolean?{
        return mSpdUtils?.getBoolean(msg,true)
    }
}