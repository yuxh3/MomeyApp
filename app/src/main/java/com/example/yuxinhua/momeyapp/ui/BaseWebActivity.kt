package com.example.yuxinhua.momeyapp.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.yuxinhua.momeyapp.R
import kotlinx.android.synthetic.main.web_activity.*

/**
 * Created by yuxh3
 * On 2018/3/22.
 * Email by 791285437@163.com
 */
class BaseWebActivity:AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_activity)
        getData()
    }

    fun getData(){
        web_view.loadUrl(intent.getStringExtra("url"))
    }
}