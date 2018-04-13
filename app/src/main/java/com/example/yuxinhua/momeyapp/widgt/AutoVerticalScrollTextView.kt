package com.example.yuxinhua.momeyapp.widgt
import android.content.Context
import android.graphics.Camera
import android.graphics.Color
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.TextSwitcher
import android.widget.TextView
import android.widget.ViewSwitcher


/**
 * 自动垂直滚动的TextView
 */
class AutoVerticalScrollTextView @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null) : TextSwitcher(mContext, attrs), ViewSwitcher.ViewFactory {

    //mInUp,mOutUp分别构成向下翻页的进出动画
    private var mInUp: Rotate3dAnimation? = null
    private var mOutUp: Rotate3dAnimation? = null
    private var mStrings: List<String>? = null
    private var number = 0
    private val mState: Boolean = false//是否在前台
    private var mOnClickListener:OnClickListener? = null

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 199) {
                next()
                number++
                setText(mStrings!![number % mStrings!!.size])
                this.sendEmptyMessageDelayed(199, 3000)
            }

        }
    }

    init {
        init()

    }

    private fun init() {

        setFactory(this)

        mInUp = createAnim(true, true)
        mOutUp = createAnim(false, true)

        inAnimation = mInUp//当View显示时动画资源ID
        outAnimation = mOutUp//当View隐藏是动画资源ID。

    }

    fun initData(strings: List<String>?) {
        val before = System.currentTimeMillis()
        if (strings != null && strings.size > 0) {
            mStrings = strings
            setText(mStrings!![0])
            if (mStrings!!.size > 1) {
                handler.removeMessages(199)
                handler.sendEmptyMessageDelayed(199, 5000)
            }
        }
        val AutoVertical = System.currentTimeMillis() - before
    }

    private fun createAnim(turnIn: Boolean, turnUp: Boolean): Rotate3dAnimation {

        val rotation = Rotate3dAnimation(turnIn, turnUp)
        rotation.duration = 500//执行动画的时间
        rotation.fillAfter = false//是否保持动画完毕之后的状态
        rotation.interpolator = AccelerateInterpolator()//设置加速模式

        return rotation
    }


    //这里返回的TextView，就是我们看到的View,可以设置自己想要的效果
    override fun makeView(): View {

        val textView = TextView(mContext)
        textView.gravity = Gravity.LEFT
        textView.textSize = 14f
        textView.setSingleLine(true)
        textView.gravity = Gravity.CENTER_VERTICAL
        textView.ellipsize = TextUtils.TruncateAt.END
        textView.setTextColor(Color.parseColor("#333333"))
        return textView

    }

    //定义动作，向上滚动翻页
    operator fun next() {
        //显示动画
        if (inAnimation !== mInUp) {
            inAnimation = mInUp
        }
        //隐藏动画
        if (outAnimation !== mOutUp) {
            outAnimation = mOutUp
        }
    }

    internal inner class Rotate3dAnimation(private val mTurnIn: Boolean, private val mTurnUp: Boolean) : Animation() {
        private var mCenterX: Float = 0.toFloat()
        private var mCenterY: Float = 0.toFloat()
        private var mCamera: Camera? = null

        override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
            super.initialize(width, height, parentWidth, parentHeight)
            mCamera = Camera()
            mCenterY = getHeight().toFloat()
            mCenterX = getWidth().toFloat()
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {

            val centerX = mCenterX
            val centerY = mCenterY
            val camera = mCamera
            val derection = if (mTurnUp) 1 else -1

            val matrix = t.matrix

            camera!!.save()
            if (mTurnIn) {
                camera.translate(0.0f, derection.toFloat() * mCenterY * (interpolatedTime - 1.0f), 0.0f)
            } else {
                camera.translate(0.0f, derection.toFloat() * mCenterY * interpolatedTime, 0.0f)
            }
            camera.getMatrix(matrix)
            camera.restore()

            matrix.preTranslate(-centerX, -centerY)
            matrix.postTranslate(centerX, centerY)
        }
    }

    fun setOnClickListener(onClickListener:OnClickListener){
        this.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                onClickListener.cliclk(mStrings!![number % mStrings!!.size])
            }

        })
    }
    interface OnClickListener{
        fun cliclk(name:String)
    }


}
