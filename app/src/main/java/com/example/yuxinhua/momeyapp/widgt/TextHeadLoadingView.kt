package com.example.yuxinhua.momeyapp.widgt

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.support.annotation.StringRes
import android.util.AttributeSet
import android.view.View
import com.example.yuxinhua.momeyapp.utils.DevicesInfo

/**
 * Created by yuxh3
 * On 2018/3/27.
 * Email by 791285437@163.com
 */
class TextHeadLoadingView:View{

    private var mPath:Path? = null
    private var mTextColor:Int = -1
    private var mTextPaint: Paint? = null
    private var MIN_HEIGHT = 200
    private var PAINT_TEXTSIZE = 23
    private var PAINT_TEXT_BASEIINE = PAINT_TEXTSIZE
    private var mResText:String = ""
    private var mTextHeight:Int = -1
    private var mTextWidth = -1
    private var mDefaultText:String = ""
    private val DEFAULT_RECF_SPACE = 6//默认的画弧形的时候的间距,值越大速度越快，不能超过最大值
    private val MAX_RECF_SPACE = 36//最大的画弧形的时候的间距
    private val MIN_RECF_SPACE = -16//12最大的画弧形的时候的间距
    private var mRecfSpace = 0//矩形RECF间距
    private val STATUS_DOWN_CURVE = 0//向下弯曲的状态
    private val STATUS_UP_CURVE = 1//向上恢复的状态
    private val STATUS_FLAT_CURVE = 2//平的状态
    private var mCurveStatus = STATUS_FLAT_CURVE
    private val MAX_SPRING_COUNT = 18//来回弹动的时间
    private var mSringCount = MAX_SPRING_COUNT//当前弹动的次数

    constructor(context: Context?) : super(context){
        initPaint()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        initPaint()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        initPaint()
    }

    fun initPaint(){
        mDefaultText = "Please wait..."
        mResText = mDefaultText
        mTextPaint = Paint()
        mTextPaint?.color = Color.parseColor("#999999")
        mTextPaint?.isAntiAlias = true

        PAINT_TEXTSIZE = DevicesInfo.dpToPx(context,12f)
        PAINT_TEXT_BASEIINE = PAINT_TEXTSIZE

        mTextPaint?.textSize = PAINT_TEXTSIZE.toFloat()
        mTextPaint?.style = Paint.Style.FILL_AND_STROKE
        mTextPaint?.textAlign = Paint.Align.LEFT

        mTextWidth = mTextPaint?.measureText(mResText)?.toInt()!!+4
        mTextHeight = DevicesInfo.dpToPx(context,20f)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(mTextWidth,mTextHeight)
    }

    fun setTextColor(color:Int){
        mTextColor = color
    }
    fun startAnim(){
        mSringCount = 0
        mCurveStatus = STATUS_DOWN_CURVE
        invalidate()
    }
    fun setText(@StringRes res:Int){
        mResText = context.getString(res)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawLinePathAndText(canvas)
    }

    fun drawLinePathAndText(canvas: Canvas?){
        if (mPath == null){
            mPath =  Path()
            drawLinePath()
        }else{
            drawArcPath()
            mRecfSpace = getRecfSpace()
            if (mRecfSpace >= MAX_RECF_SPACE){
                mCurveStatus = STATUS_UP_CURVE
            }else if (mRecfSpace <= MIN_RECF_SPACE){
                mCurveStatus = STATUS_DOWN_CURVE
            }
        }

        if (mSringCount < MAX_SPRING_COUNT){
            mSringCount++
            invalidate()
        }else {
            reset(canvas)
        }
        canvas?.drawTextOnPath(mResText,mPath,0f,0f,mTextPaint)
    }

    fun reset(canvas: Canvas?){
        mRecfSpace = 0
        drawArcPath()
        mCurveStatus = STATUS_FLAT_CURVE
    }
    fun getRecfSpace():Int{
        if (mCurveStatus == STATUS_DOWN_CURVE){
            return mRecfSpace + DEFAULT_RECF_SPACE
        }else if (mCurveStatus == STATUS_UP_CURVE){
            return mRecfSpace -DEFAULT_RECF_SPACE
        }else{
            return 0
        }

    }
    fun drawLinePath(){
        mPath?.moveTo(0f,PAINT_TEXT_BASEIINE.toFloat())
        mPath?.lineTo(mTextWidth.toFloat(),PAINT_TEXT_BASEIINE.toFloat())
        mPath?.close()
    }
    fun drawArcPath(){
        mPath?.reset()
        mPath?.moveTo(0f,PAINT_TEXT_BASEIINE.toFloat())
        mPath?.quadTo(0f,PAINT_TEXT_BASEIINE.toFloat(),5f,PAINT_TEXT_BASEIINE.toFloat())
        mPath?.quadTo(mTextWidth/2.toFloat(),(PAINT_TEXT_BASEIINE+mRecfSpace).toFloat()
        ,(mTextWidth -5).toFloat(),PAINT_TEXT_BASEIINE.toFloat())
        mPath?.quadTo(mTextWidth*5/6.toFloat(),PAINT_TEXT_BASEIINE.toFloat()
        ,mTextWidth.toFloat(),PAINT_TEXT_BASEIINE.toFloat())
        mPath?.close()
    }
}