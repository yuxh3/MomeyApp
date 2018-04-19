package com.example.yuxinhua.momeyapp.widgt

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by yuxh3
 * On 2018/4/17.
 * Email by 791285437@163.com
 */
class NoScrollViewPager:ViewPager{

    private var isSlide = false
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    fun setSlide(isSlide:Boolean){

        this.isSlide = isSlide
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return isSlide
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return isSlide && super.onTouchEvent(ev)
    }
}