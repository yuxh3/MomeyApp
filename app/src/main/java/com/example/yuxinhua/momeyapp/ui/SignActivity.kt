package com.example.yuxinhua.momeyapp.ui

import android.graphics.Color
import android.os.Bundle
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.utils.ToastUtils
import kotlinx.android.synthetic.main.invest_fragment_layout.*
import kotlinx.android.synthetic.main.sign_activity.*
import java.util.*

/**
 * Created by yuxh3
 * On 2018/4/19.
 * Email by 791285437@163.com
 */
class SignActivity:BaseActivity(){
    override fun getLayoutId(): Int  = R.layout.sign_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        isNeedShowTranslucentStatus(true,Color.TRANSPARENT)
        sign_headtextview.setPadding(0,getStatusHeight(),0,0)
        sign_headtextview.setBackgroundResource(R.color.inverst_head_yellow)
        sign_cv.setOnClickListener(object :com.example.yuxinhua.momeyapp.widgt.CalendarView.onClickListener{
            override fun onLeftRowClick() {
                ToastUtils.setShortMsg(this@SignActivity,"左边被电击了")
                sign_cv.monthChange(-1)
            }

            override fun onRightRowClick() {
                ToastUtils.setShortMsg(this@SignActivity,"右边被电击了")
                sign_cv.monthChange(1)
            }

            override fun onTitleClick(monthStr: String, month: Date) {
                ToastUtils.setShortMsg(this@SignActivity,monthStr)
            }

            override fun onWeekClick(weekIndex: Int, weekStr: String) {
                ToastUtils.setShortMsg(this@SignActivity,weekStr)
            }

            override fun onDayClick(day: Int, dayStr: String) {
                ToastUtils.setShortMsg(this@SignActivity,day.toString())
            }

        })

        sign_headtextview.getLeftImg().setOnClickListener {
            finish()
        }
    }
}