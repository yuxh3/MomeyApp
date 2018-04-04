package com.example.yuxinhua.momeyapp.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.fragment.FindFragment
import com.example.yuxinhua.momeyapp.fragment.HomeFragment
import com.example.yuxinhua.momeyapp.fragment.InvestFragment
import com.example.yuxinhua.momeyapp.fragment.MeFragment
import kotlinx.android.synthetic.main.main_bottom_layout.*


class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_main

    lateinit var viewList:ArrayList<View>
    lateinit var fragmentList:ArrayList<Fragment>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        isNeedShowTranslucentStatus(true,R.color.transparent)
        init()
    }

    override fun onStart() {
        super.onStart()

        changeTextColor(0)
        changeFragment(0)
    }
    /**
     * 初始化布局，以及底部的按钮触摸事件
     */
    fun init(){
        viewList = arrayListOf()
        viewList.add(rb_btn_1)
        viewList.add(rb_btn_2)
        viewList.add(rb_btn_3)
        viewList.add(rb_btn_4)

        fragmentList = arrayListOf()
        fragmentList.add(HomeFragment.getInstance())
        fragmentList.add(InvestFragment.getInstance())
        fragmentList.add(FindFragment.getInstance())
        fragmentList.add(MeFragment.getInstance())

        rb_group.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                when(p1){
                    R.id.rb_btn_1 ->{
                        changeTextColor(0)
                        changeFragment(0)
                    }
                    R.id.rb_btn_2 ->{
                        changeTextColor(1)
                        changeFragment(1)
                    }
                    R.id.rb_btn_3 ->{
                        changeTextColor(2)
                        changeFragment(2)
                    }
                    R.id.rb_btn_4 ->{
                        changeTextColor(3)
                        changeFragment(3)
                    }
                }
            }

        })

    }

    /**
     * 清除之前的样式
     */
    fun clearStyle(){

        for (i in 0..viewList.size -1){
            viewList.get(i).isSelected = false
            (viewList.get(i) as RadioButton).setTextColor(ContextCompat.getColor(this, R.color.txt_gray_theme))
        }
    }
    fun changeTextColor(position:Int){
        clearStyle()
        viewList.get(position).isSelected = true
        (viewList.get(position) as RadioButton).setTextColor(ContextCompat.getColor(this, R.color.txt_baclk_theme))
    }


    fun changeFragment(position: Int){
        val fragmentManager = supportFragmentManager.beginTransaction()
        for (i in 0..fragmentList.size -1){
            val fragment = fragmentList.get(i)
            if (position == i) {
                if (!fragment.isAdded) {
                    fragmentManager.add(R.id.content_fragment,fragmentList.get(position))
                }
                fragmentManager.show(fragment)
            }else{
                if (fragment.isAdded) {
                    fragmentManager.hide(fragment)
                }
            }
        }
        fragmentManager.commitAllowingStateLoss()
    }
}
