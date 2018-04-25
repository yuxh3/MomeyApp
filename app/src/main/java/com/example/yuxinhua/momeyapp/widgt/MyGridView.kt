package com.example.yuxinhua.momeyapp.widgt

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.os.Handler
import android.os.Vibrator
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.GridView
import android.widget.ImageView

/**
 * Created by yuxh3
 * On 2018/4/25.
 * Email by 791285437@163.com
 */
class MyGridView:GridView{

    // 拖拽的响应时间 默认为1秒
    private var mDragResponseMs:Long = 1000

    //是否支持拖拽 默认不支持
    private var isDrag = false

    //震动器 用于提示替换
    private var mVibrator:Vibrator? = null

    //拖拽的item 对应的position
    private var mDragPosition:Int = -1

    //拖拽的item 对应的view
    private var mDragView: View? = null

    //窗口管理器 用于界面上添加的view
    private var mWindowManaher:WindowManager? = null

    //item 镜像的布局参数
    private var mLayoutParams:WindowManager.LayoutParams? = null

    // item 镜像的显示镜像 这里使用Imageview 显示
    private var mDragMirrorView:ImageView? = null

    //item 镜像的bitmap
    private var mDragBitmap:Bitmap? = null

    // 按下的点到所在item的左边缘距离
    private var mPotion2ItemLeft = -1
    private var mPotion2ItemTop = -1

    //DragView到上边缘的距离
    private var mOffset2Top = -1
    private var mOffset2Left =-1

    //按下时x,y
    private var mDownX: Int = 0
    private var mDownY: Int = 0
    //移动的时x.y
    private var mMoveX: Int = 0
    private var mMoveY: Int = 0

    //状态栏高度
    private var mStatusHeight: Int = 0

    //XGridView向下滚动的边界值
    private var mDownScrollBorder: Int = 0
    //XGridView向上滚动的边界值
    private var mUpScrollBorder: Int = 0
    //滚动的速度
    private var mSpeed = 20

    //item发生变化的回调接口
//    private var changeListener: OnItemChangeListener? = null

    private var mHandler: Handler? = null


    private val mLongClickRunable = object :Runnable{
        @SuppressLint("MissingPermission")
        override fun run() {
            isDrag = true
            mVibrator?.vibrate(200)
            //隐藏该item
            mDragView?.visibility = View.INVISIBLE

            //在点击的地方创建并显示item镜像
            createDragView(mDragBitmap,mDownX,mDownY)

        }

    }

    private fun createDragView(mBitmap: Bitmap?, mDownX: Int, mDownY: Int) {
        mLayoutParams = WindowManager.LayoutParams()
        mLayoutParams?.format = PixelFormat.TRANSLUCENT //图片之外其他地方透明
        mLayoutParams?.gravity = Gravity.TOP and Gravity.LEFT
        //指定位置 其实就是 该 item 对应的 rawX rawY 因为Window 添加View是需要知道 raw x ,y的

        mLayoutParams?.x = mOffset2Left+(mDownX - mPotion2ItemLeft)
        mLayoutParams?.y = mOffset2Top + (mDownY - mPotion2ItemTop)

        //指定布局的大小
        mLayoutParams?.width = WindowManager.LayoutParams.WRAP_CONTENT
        mLayoutParams?.height = WindowManager.LayoutParams.WRAP_CONTENT

        //透明度
        mLayoutParams?.alpha = 0.4f
        //指定标志 不能获取焦点和触摸
        mLayoutParams?.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE and WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE

        mDragMirrorView = ImageView(context)
        mDragMirrorView?.setImageBitmap(mBitmap)
        //添加view到窗口中
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
}