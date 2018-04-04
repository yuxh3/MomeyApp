package com.example.yuxinhua.momeyapp.widgt

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.example.yuxinhua.momeyapp.R

/**
 * Created by yuxh3
 * On 2018/4/4.
 * Email by 791285437@163.com
 */
class ProgressView: View {

    constructor(context: Context?) : super(context){

        initView(context)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        initView(context)
    }

    fun initView(context: Context?){

        val view = LayoutInflater.from(context).inflate(R.layout.progress_view,null)

    }
}