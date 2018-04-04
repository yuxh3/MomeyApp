package com.example.yuxinhua.momeyapp.widgt

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.yuxinhua.momeyapp.R

/**
 * Created by yuxh3
 * On 2018/4/3.
 * Email by 791285437@163.com
 */
class WaveView:View{

    private var mWidth:Int = 0
    private var mHeight:Int = 0
    private var mBaseLine:Int = 0 //基线，用于控制水位上涨的，这里是写死了没动，你可以不断的设置改变。
    private var mPaint:Paint? = null

    private var waveHeight = 20 //波浪的最高点
    private var waveWidth = 0

    private var offset = 0f

    constructor(context: Context?) : super(context){
        initView(context)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        initView(context)
    }

    fun initView(context: Context?){

        mPaint = Paint()
        mPaint?.color = context?.resources?.getColor(R.color.txt_witch_theme)?:Color.parseColor("#FFEAC04B")
        mPaint?.style = Paint.Style.FILL
    }

    /**
     * 不断的更新偏移量，并且循环
     */
    fun upDateXControl(){

        val mAnimator = ValueAnimator.ofFloat(0f,waveWidth.toFloat())
        mAnimator.interpolator = LinearInterpolator()

        mAnimator.addUpdateListener(object :ValueAnimator.AnimatorUpdateListener{
            override fun onAnimationUpdate(p0: ValueAnimator?) {
                val animationValue = p0?.animatedValue.toString().toFloat()
                offset = animationValue
                postInvalidate()
            }
        })

        mAnimator.repeatCount = ValueAnimator.INFINITE
        mAnimator.setDuration(2000)
        mAnimator.start()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        mWidth = measuredWidth
        mHeight = measuredHeight

        waveWidth = mWidth
        mBaseLine = height/3

        upDateXControl()
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawPath(getPath(),mPaint)
        mPaint?.color
    }

    fun getPath():Path{
        val itemWidth = waveWidth /2
        val mPath = Path()
        mPath.moveTo(-itemWidth * 3f, mBaseLine.toFloat())

        for (i in -3..1){
            val startX = i * itemWidth
            mPath.quadTo(startX + itemWidth/2 + offset,
                    getWaveHeigh(i),
                    startX + itemWidth + offset,
                    mBaseLine.toFloat())
        }

        mPath.lineTo(mWidth.toFloat(),mHeight.toFloat())
        mPath.lineTo(0f,height.toFloat())
        mPath.close()
        return  mPath
    }

    //奇数峰值是正的，偶数峰值是负数
    private fun getWaveHeigh(num: Int): Float {
        return if (num % 2 == 0) {
            (mBaseLine + waveHeight).toFloat()
        } else (mBaseLine - waveHeight).toFloat()
    }
}