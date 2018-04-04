package com.example.yuxinhua.momeyapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.security.keystore.KeyGenParameterSpec
import android.view.View
import android.view.animation.AlphaAnimation
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.utils.SPDUtils
import com.example.yuxinhua.momeyapp.widgt.MyAppActivity
import com.example.yuxinhua.momeyapp.widgt.RecentLoadingView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.network_layout.*
import kotlinx.android.synthetic.main.splash_acitvity.*
import android.content.ComponentName
import android.app.ActivityManager
import android.content.Context
import android.view.KeyEvent
import android.view.MotionEvent


/**
 * Created by yuxh3
 * On 2018/3/16.
 * Email by 791285437@163.com
 */
class SplashAcitvity:BaseActivity(){
    override fun getLayoutId(): Int = R.layout.splash_acitvity

    var timer:CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_acitvity)
        isNeedShowTranslucentStatus(true,R.color.transparent)
    }

    override fun onResume() {
        super.onResume()

        if (mSpdUtils?.getBoolean("isUrseFrign")!! && mSpdUtils?.getBoolean("isOpen")!!){
            val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val cn = am.getRunningTasks(1)[0].topActivity
            if (cn.className.contains(MyAppActivity::class.java!!.getName())) {
                return
            }
            startActivity(Intent(this@SplashAcitvity, MyAppActivity::class.java))
        }else{
            if (mSpdUtils?.getString("isStart")?.equals("success")!!){
                startApp()
                mSpdUtils?.setString("isOnClick","open")
            }else if (mSpdUtils?.getString("isStart")?.equals("error")!!){
                mSpdUtils!!.setBoolean("isOpen", true)
                finish()
            }else if (mSpdUtils?.getString("isStart")?.equals("cancel")!!){
                mSpdUtils!!.setBoolean("isOpen", true)
                finish()
            }else if (mSpdUtils?.getString("isOnClick")?.equals("open")!!){
                startApp()
            }
        }
    }
    fun startApp(){
        init()
        tv_djup.setOnClickListener {

            if (mSpdUtils?.getBoolean("isFrist")!!) {
                mSpdUtils?.setBoolean("isFrist",false)
                startActivity(Intent(this@SplashAcitvity, GuideActivity::class.java))
            }else{
                startActivity(Intent(this@SplashAcitvity, MainActivity::class.java))
            }
            finish()
        }
    }
    fun init(){
        Picasso.with(this).load("http://www.yufex.com/app/native/startup/startup.png").into(iv_visibliy)
        annimotion()
        timer = object : CountDownTimer(4000L,1000L){
            override fun onFinish() {

                if (mSpdUtils?.getBoolean("isFrist")!!) {
                    mSpdUtils?.setBoolean("isFrist",false)
                    startActivity(Intent(this@SplashAcitvity, GuideActivity::class.java))
                }else{
                    startActivity(Intent(this@SplashAcitvity, MainActivity::class.java))
                }
                finish()
            }

            override fun onTick(p0: Long) {

            }

        }
        timer?.start()
    }

    fun annimotion(){
        iv_visibliy.visibility = View.VISIBLE
        val annitiom = AlphaAnimation(0f,1f)
        annitiom.duration = 1500
        annitiom.start()
        iv_visibliy.animation = annitiom
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}