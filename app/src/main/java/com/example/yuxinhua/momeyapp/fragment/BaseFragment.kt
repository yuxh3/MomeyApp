package com.example.yuxinhua.momeyapp.fragment

import android.graphics.Color
import android.os.*
import android.support.v4.app.Fragment
import android.view.WindowManager
import com.example.notwork.mvp.model.HomeModel
import com.example.notwork.mvp.present.HomePresent
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.ui.BaseActivity
import com.example.yuxinhua.momeyapp.widgt.UIHandler
import com.readystatesoftware.systembartint.SystemBarTintManager

/**
 * Created by yuxh3
 * On 2018/3/23.
 * Email by 791285437@163.com
 */
open class BaseFragment:Fragment() {

    var mHandler:UIHandler? = null
    fun isNeedShowTranslucentStatus(isNeed:Boolean,colorId:Int){
        setTranslucentStatus(isNeed)
        initSystemBarTintManager(colorId)
    }
    fun setTranslucentStatus(isNeed: Boolean){
        val win = activity.window
        val winParams = win.attributes
        val bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        if (isNeed) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    /**
     * 初始化顶部状态栏
     */
    private fun initSystemBarTintManager(colorId:Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                val decorViewClazz = Class.forName("com.android.internal.policy.DecorView")
                val field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor")
                field.isAccessible = true
                field.setInt(activity.window.decorView, Color.TRANSPARENT)  //改为透明
            } catch (e: Exception) {
            }

        }
        val tintManager = SystemBarTintManager(activity)
        tintManager.setStatusBarTintEnabled(true)
        tintManager.setStatusBarTintResource(Color.TRANSPARENT)//通知栏所需颜色
        // set a custom tint color for all system bars
        //        tintManager.setTintColor(Color.parseColor("#99000FF"));
        // set a custom navigation bar resource
        tintManager.setNavigationBarTintResource(R.mipmap.ic_launcher)
    }

    fun getStatusHeight():Int{
        var statusBarHeight = -1
        //获取status_bar_height资源的ID
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    var mPresenter:HomePresent? = null
    var mModel:HomeModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mHandler = UIHandler(Looper.getMainLooper(),object :UIHandler.IHandler{
            override fun handleMessage(msg: Message?) {
                handlerMessage(msg)
            }

        })

        mPresenter = HomePresent()
        mModel = HomeModel()
    }

    open fun handlerMessage(msg: Message?){

    }

    fun addAnimation(isShow:Boolean){
        (activity as BaseActivity).setLoadingViewShow(isShow)
    }

}