package com.example.yuxinhua.momeyapp.widgt

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.os.SystemClock
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.example.yuxinhua.momeyapp.R

/**
 * Created by yuxh3
 * On 2018/3/27.
 * Email by 791285437@163.com
 */
class CircleProgressBar : View {


    internal var mTypedArray:TypedArray? = null
    internal var roundColor:Int= -1
    internal var roundProgressColor:Int = -1
    internal var textColor:Int = -1
    internal var textSize:Float = -1f
    internal var roundWidth:Float = 0f
    internal var mText:String = "nihao"
    internal var mMax:Int = -1
    internal var mTextIsDisplayable:Boolean = false
    internal var mStyle:Int = -1
    internal var progress:Int = 0
    internal var mPaint:Paint? = null

    companion object {

        val STROKE = 0
        val FILL = 1
    }

    constructor(context: Context):super(context){

    }
    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){
        mPaint = Paint()

        mTypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.RoundProgressBar)
        roundColor = mTypedArray?.getColor(R.styleable.RoundProgressBar_roundColor, Color.WHITE)!!
        roundProgressColor = mTypedArray?.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.WHITE)!!
        textColor = mTypedArray?.getColor(R.styleable.RoundProgressBar_textColor, Color.BLACK)!!
        textSize = mTypedArray?.getDimension(R.styleable.RoundProgressBar_textSize, 15f)!!
        roundWidth  = mTypedArray?.getDimension(R.styleable.RoundProgressBar_roundWidth,10F)!!
        mText = mTypedArray?.getString(R.styleable.RoundProgressBar_text)!!
        mMax = mTypedArray?.getInteger(R.styleable.RoundProgressBar_max, 100)!!
        mTextIsDisplayable = mTypedArray?.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true)!!
        mStyle = mTypedArray?.getInt(R.styleable.RoundProgressBar_style, 0)!!

        mTypedArray?.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val radius = (width - roundWidth)/2
        val centerX = width/2

        //
        mPaint?.color = resources.getColor(R.color.txt_gray_theme)
        mPaint?.style = Paint.Style.STROKE
        mPaint?.strokeWidth = 6f
        mPaint?.isAntiAlias = true
        canvas?.drawCircle(centerX.toFloat(),centerX.toFloat(),radius,mPaint)


        val textPaint = TextPaint()

        textPaint.color = textColor
        textPaint.typeface = Typeface.DEFAULT
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = textSize
//
//        mPaint?.color = textColor
//        mPaint?.textSize = textSize
//        mPaint?.strokeWidth = 0f
//        mPaint?.textAlign = Paint.Align.CENTER
//        mPaint?.typeface = Typeface.DEFAULT

        val stat = StaticLayout(mText,textPaint,240, Layout.Alignment.ALIGN_NORMAL,1.0F,0.0F,true)
        val textWidth = mPaint?.measureText(mText)



        val fontMetrics = mPaint?.getFontMetricsInt()
        val baseline = (measuredHeight - fontMetrics?.bottom!!+fontMetrics?.top)/2 - fontMetrics?.top

        //画布的位置得偏移下，不然不会居中
        canvas?.translate(centerX.toFloat(),height/4.toFloat())
        stat.draw(canvas)


        //恢复画布的位置
        canvas?.translate(-centerX.toFloat(),-height/4.toFloat())
//        canvas?.drawText(mText,centerX.toFloat(),baseline.toFloat(),mPaint)


        mPaint?.strokeWidth = 6f
        mPaint?.color = resources.getColor(R.color.btn_theme_red)
        mPaint?.style = Paint.Style.FILL

        val oval =  RectF(centerX - radius,centerX - radius,centerX + radius,centerX + radius)

        when(mStyle){
            STROKE->{
                mPaint?.style = Paint.Style.STROKE
                canvas?.drawArc(oval,-90f,(360*progress/mMax).toFloat(),false,mPaint)
            }
            FILL->{
                mPaint?.style = Paint.Style.FILL
                if (progress != 0){
                    canvas?.drawArc(oval,0f,(360*progress/mMax).toFloat(),false,mPaint)
                }
            }
        }

    }

    var isStop:Boolean = false

    fun start(){
        Thread(object :Runnable {
            override fun run() {
                setReset()
                if (!isStop){
                    while (mCountProgress <=mMax){
                        progress +=1

//                        setProgress(progress)
                        postInvalidate()
                        SystemClock.sleep(30)
                        if (progress == mCountProgress){
                            isStop = true
                            break
                        }
                    }
                }
            }

        }).start()
    }

    fun getMax():Int{
        return mMax
    }

    fun setMax(max:Int){
        if (max <0){
            throw IllegalArgumentException("max not less than 0")
        }
    }

    fun getCountProgress():Int{
        return mCountProgress
    }

    fun setReset(){
        this.progress = 0
        isStop = false
    }
    fun setProgress(progres:Int){
        if (progres <0){
            throw IllegalArgumentException("max not less than 0")
        }

        if (progres > mMax){
            this.mCountProgress = progres
        }

        if (progres<=mMax){
            this.mCountProgress = progres
        }
    }
    var mCountProgress  = 0
}