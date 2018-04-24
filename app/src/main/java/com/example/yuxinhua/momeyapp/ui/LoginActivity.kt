package com.example.yuxinhua.momeyapp.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.fragment.LogFragment
import com.example.yuxinhua.momeyapp.fragment.RegistFragment
import kotlinx.android.synthetic.main.login_activity.*

/**
 * Created by yuxh3
 * On 2018/4/16.
 * Email by 791285437@163.com
 */
class LoginActivity:BaseActivity(){


    val fragmentList:ArrayList<Fragment> = arrayListOf()

    val titleList:ArrayList<String> = arrayListOf("登录","注册")
    override fun getLayoutId(): Int = R.layout.login_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())

        isNeedShowTranslucentStatus(true,Color.TRANSPARENT)
        initFragment()

        initViewPager()

        iv_back.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                finish()
            }

        })
    }


    fun initFragment(){
        fragmentList.add(LogFragment.getInstance())
        fragmentList.add(RegistFragment.getInstance())
    }

    fun initViewPager(){
        val mAdapter = object : FragmentPagerAdapter(supportFragmentManager){

            override fun getItem(position: Int): Fragment = fragmentList.get(position)

            override fun getCount(): Int = titleList.size

        }

        my_layout.setTabItemTitles(titleList)

        my_layout.setViewPager(login_viewpager,0)
        login_viewpager.adapter = mAdapter

        login_viewpager.setSlide(false)
    }

    fun setFinish(){

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

}