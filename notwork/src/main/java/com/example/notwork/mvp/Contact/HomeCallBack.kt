package com.example.notwork.mvp.Contact

import com.example.notwork.`object`.BenLaiItemData

/**
 * Created by yuxh3
 * On 2018/4/11.
 * Email by 791285437@163.com
 */
interface HomeCallBack{

    fun success(data: BenLaiItemData)
    fun error(code:Int,msg:String)
}