package com.example.yuxinhua.momeyapp.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.utils.SPDUtils
import com.example.yuxinhua.momeyapp.widgt.RecentLoadingView
import com.readystatesoftware.systembartint.SystemBarTintManager
import kotlinx.android.synthetic.main.network_layout.*

/**
 * Created by yuxh3
 * On 2018/3/23.
 * Email by 791285437@163.com
 */
abstract open class BaseActivity :FragmentActivity() {

    var mFragmLonding:FrameLayout? = null

    var mFrameLayout:FrameLayout? = null
    var mSpdUtils:SPDUtils? = null
    abstract fun getLayoutId():Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSpdUtils = SPDUtils(this)

    }
    fun setInitBaseView(layoutResID: Int){
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        viewGroup.removeAllViews()
        mFrameLayout = FrameLayout(this)
        viewGroup.addView(mFrameLayout)
        LayoutInflater.from(this).inflate(layoutResID,mFrameLayout)
    }
    override fun setContentView(layoutResID: Int) {
         super.setContentView(layoutResID)
        setInitBaseView(layoutResID)
        LayoutInflater.from(this).inflate(R.layout.network_layout,mFrameLayout)
        mFragmLonding = fl_view as FrameLayout
    }

    override fun setContentView(view: View?) {
        mFrameLayout?.addView(view)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        mFrameLayout?.addView(view,params)
    }

    fun isNeedShowTranslucentStatus(isNeed:Boolean,colorId:Int){
        setTranslucentStatus(isNeed)
        initSystemBarTintManager(colorId)
    }
    fun setTranslucentStatus(isNeed: Boolean){
        val win = window
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
                field.setInt(window.decorView, Color.TRANSPARENT)  //改为透明
            } catch (e: Exception) {
            }

        }
        val tintManager = SystemBarTintManager(this)
        tintManager.setStatusBarTintEnabled(true)
        tintManager.setStatusBarTintResource(colorId)//通知栏所需颜色
        // set a custom tint color for all system bars
        //        tintManager.setTintColor(Color.parseColor("#99000FF"));
        // set a custom navigation bar resource
        tintManager.setNavigationBarTintResource(R.mipmap.ic_launcher)
    }

    /**
     * 获取动画view
     */
    fun getLoadingView():RecentLoadingView{

       return mFragmLonding?.getChildAt(0) as RecentLoadingView
    }

    fun getNetWorkView():LinearLayout{
        return ll_net_error
    }

    fun setAnimaListener(isShow: Boolean,animationListener:RecentLoadingView.OnViewAnimEndListener){
        setLoadingViewShow(isShow)
        getLoadingView().setOnViewAnimEndListener(object : RecentLoadingView.OnViewAnimEndListener{
            override fun onDropDown() {
                animationListener.onDropDown()
            }

        })
    }
    /**
     * 显示加载动画
     */
    fun setLoadingViewShow(isShow:Boolean){
        if (isShow) {
            mFragmLonding?.visibility = View.VISIBLE
        }else{
            mFragmLonding?.visibility = View.GONE
        }
    }

    /**
     * 没网络的时候显示
     */
    fun setNetWorkViewShow(isShow:Boolean){
        if (isShow) {
            ll_net_error.visibility = View.VISIBLE
        }else{
            ll_net_error.visibility = View.GONE
        }
    }

    /**
     * 设置点击重新请求网络
     */
    fun setNetWorkListener(onClickListener: View.OnClickListener){
        ll_net_error.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                onClickListener.onClick(p0)
            }
        })
    }
}