package com.example.yuxinhua.momeyapp.widgt

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.os.Handler
import android.os.Vibrator
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageView
import android.widget.Switch

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
    private var changeListener: OnItemChangeListener? = null

    private var mHandler: Handler? = null


    private val mLongClickRunable = object :Runnable{
//        @SuppressLint("MissingPermission")
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

//        mLayoutParams?.x = mOffset2Left+(mDownX - mPotion2ItemLeft)
        mLayoutParams?.x = mDownX
//        mLayoutParams?.y = mOffset2Top + (mDownY - mPotion2ItemTop)
        mLayoutParams?.y = mDownY

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
        mWindowManaher?.addView(mDragMirrorView,mLayoutParams)
    }

    constructor(context: Context?) : super(context){}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        mVibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        mWindowManaher = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mHandler = Handler()
        mStatusHeight = getStatusHeight()
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


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        when (ev?.getAction()) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = ev.getX().toInt()
                mDownY = ev.getY().toInt()

                //获取按下的position
                mDragPosition = pointToPosition(mDownX, mDownY)
                if (mDragPosition === AdapterView.INVALID_POSITION) {     //无效就返回
                    return super.dispatchTouchEvent(ev)
                }

                //延时长按执行mLongClickRunable
                mHandler?.postDelayed({
                    isDrag = true
                    mVibrator?.vibrate(200)
                    //隐藏该item
                    mDragView?.visibility = View.INVISIBLE

                    //在点击的地方创建并显示item镜像
                    createDragView(mDragBitmap,mDownX,mDownY)
                }, 200)
                //获取按下的item对应的View 由于存在复用机制，所以需要 处理FirstVisiblePosition
                mDragView = getChildAt(mDragPosition - firstVisiblePosition)
                if (mDragView == null) {
                    return super.dispatchTouchEvent(ev)
                }

                //计算按下的点到所在item的left top 距离
                mPotion2ItemLeft = mDownX - mDragView?.getLeft()!!
                mPotion2ItemTop = mDownY - mDragView?.getTop()!!
                //计算GridView的left top 偏移量：原始距离 - 相对距离就是偏移量
                mOffset2Left = ev.getRawX().toInt() - mDownX
                mOffset2Top = ev.getRawY().toInt() - mDownY

                //获取GridView自动向下滚动的偏移量，小于这个值，DragGridView向下滚动
                mDownScrollBorder = height / 4
                //获取GridView自动向上滚动的偏移量，大于这个值，DragGridView向上滚动
                mUpScrollBorder = height * 3 / 4

                //开启视图缓存
                mDragView?.setDrawingCacheEnabled(true)
                //获取缓存的中的bitmap镜像 包含了item中的ImageView和TextView
                mDragBitmap = Bitmap.createBitmap(mDragView?.getDrawingCache())
                //释放视图缓存 避免出现重复的镜像
                mDragView?.destroyDrawingCache()
            }
            MotionEvent.ACTION_MOVE -> {

                mMoveX = ev.getX().toInt()
                mMoveY = ev.getY().toInt()

                //如果只在按下的item上移动，未超过边界，就不移除mLongClickRunable
                if (!isTouchInItem(mDragView!!, mMoveX, mMoveY)) {
                    mHandler?.removeCallbacks(mLongClickRunable)
                }
            }
            MotionEvent.ACTION_UP -> {
                mHandler?.removeCallbacks(mLongClickRunable)
                mHandler?.removeCallbacks(mScrollRunbale)
            }
            else -> {
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (isDrag && mDragMirrorView != null){
            when(ev?.action){
                MotionEvent.ACTION_DOWN->{

                }
                MotionEvent.ACTION_MOVE ->{
                    mMoveX = ev.x.toInt()
                    mDownY = ev.y.toInt()
                    onDragItem(mMoveX,mDownY)
                }
                MotionEvent.ACTION_UP->{
                    onStopDrag()
                    isDrag = false
                }
            }
            return true
        }
        return super.onTouchEvent(ev)
    }

    /**
     *
     */
    fun setDrag(drag:Boolean){
        isDrag = drag
    }
    fun getDragResponseMs():Long{
        return mDragResponseMs
    }

    fun setDragResponseMs(mDragResponseMs:Long){
        this.mDragResponseMs = mDragResponseMs
    }

    fun setOnItemChangeListener(chaneListener:OnItemChangeListener){
        this.changeListener = chaneListener
    }

    /**
     * 点是否在该view上面
     */
    fun isTouchInItem(view: View,x:Int,y:Int):Boolean{
        if (view == null)
            return false
        if (view.left<x && view.right>x && view.top<y&& view.bottom<y)
            return true
        else
            return false

    }

    fun onStopDrag(){
        val view = getChildAt(mDragPosition - firstVisiblePosition)
        if (view != null)
            view.visibility = View.VISIBLE
        removeDragImag()
    }

    /**
     * 移除镜像
     */
    fun removeDragImag(){
        if (mDragMirrorView != null){
            mWindowManaher?.removeView(mDragMirrorView)
            mDragMirrorView = null
        }
    }

    /**
     * 移动item到指定的位置
     */
    fun onDragItem(x:Int,y: Int){
        mLayoutParams?.x = x - mPotion2ItemLeft+mOffset2Left
        mLayoutParams?.y = y - mPotion2ItemTop+mOffset2Top
        //更新镜像的位置
        mWindowManaher?.updateViewLayout(mDragMirrorView!!,mLayoutParams)

        onSwapItem(x,y)
        mHandler?.post(mScrollRunbale)
    }

    /**
     * 交换 item 并且控制 item之间的显示与隐藏
     * @param x
     * @param y
     */
    private fun onSwapItem(x: Int, y: Int) {
        //获取我们手指移动到那个item
        val tmpPosition = pointToPosition(x, y)
        if (tmpPosition != AdapterView.INVALID_POSITION && tmpPosition != mDragPosition) {
            if (changeListener != null) {
                changeListener?.onChange(mDragPosition, tmpPosition)
            }
            //隐藏tmpPosition
            getChildAt(tmpPosition - firstVisiblePosition).visibility = View.INVISIBLE
            //显示之前的item
            getChildAt(mDragPosition - firstVisiblePosition).visibility = View.VISIBLE

            mDragPosition = tmpPosition
        }

    }

    /**
     * item 交换时的回调接口
     */
    interface OnItemChangeListener {
        fun onChange(from: Int, to: Int)
    }

    /**
     * 当moveY的值大于向上滚动的边界值，触发GridView自动向上滚动
     * 当moveY的值小于向下滚动的边界值，触犯GridView自动向下滚动
     * 否则不进行滚动
     */
    private val mScrollRunbale = object : Runnable {
        override fun run() {
            var scrollY = 0
            if (mMoveY > mUpScrollBorder) {
                scrollY = mSpeed
                mHandler?.postDelayed(this, 25)
            } else if (mMoveY < mDownScrollBorder) {
                scrollY = -mSpeed
                mHandler?.postDelayed(this, 25)
            } else {
                scrollY = 0
                mHandler?.removeCallbacks(this)
            }
            smoothScrollBy(scrollY, 10)
        }
    }
}