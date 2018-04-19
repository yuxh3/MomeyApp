package com.example.yuxinhua.momeyapp.widgt

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.NinePatchDrawable
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.utils.DevicesInfo

/**
 * Created by yuxh3
 * On 2018/3/23.
 * Email by 791285437@163.com
 */
class RVPindicator @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        /*
     * 系统默认:Tab数量
     */
        var D_TAB_COUNT = 2

        /*
         * 系统默认:指示器颜色
         */
        var D_INDICATOR_COLOR = Color.parseColor("#DCDCDC")
        /**
         * 可见tab数量
         */
        var mTabVisibleCount = D_TAB_COUNT

        /**
         * 指示器正常时的颜色
         */
        private var mIndicatorColor = D_INDICATOR_COLOR

    }

    /**
     * 系统默认:文字正常时颜色
     */
    var D_TEXT_COLOR_NORMAL = Color.parseColor("#FF0000")

    /*
     * 系统默认:文字选中时颜色
     */
    var D_TEXT_COLOR_HIGHLIGHT = Color.parseColor("#FF0000")


    /**
     * 文字正常时的颜色
     */
    var mTextColorNormal = D_TEXT_COLOR_NORMAL

    /**
     * 文字选中时的颜色
     */
    private var mTextColorHighlight = D_TEXT_COLOR_HIGHLIGHT

    /**
     * 文字大小
     */
    private var mTextSize = 16


    /**
     * 画笔
     */
    private var mPaint: Paint? = null

    /**
     * 矩形
     */
    private var mRectF: Rect? = null

    /**
     * bitmap
     */
    private var mBitmap: Bitmap? = null

    /**
     * 指示器高
     */
    private var mIndicatorHeight = 200

    /**
     * 指示器宽
     */
    private var mIndicatorWidth = width / mTabVisibleCount
    /**
     * 三角形的宽度为单个Tab的1/6
     */
    private var RADIO_TRIANGEL = 1.0f / 6

    /**
     * 手指滑动时的偏移量
     */
    private var mTranslationX: Float = 0.toFloat()

    /**
     * 指示器风格
     */
    private var mIndicatorStyle = DevicesInfo.STYLE_LINE

    /**
     * 曲线path
     */
    private var mPath: Path? = null

    /**
     * viewPage初始下标
     */
    private var mPosition = 0


    init {

        setGravity(Gravity.CENTER_VERTICAL)

//        this.orientation = VERTICAL
//        setPadding(DevicesInfo.dpToPx(context,25f),0,DevicesInfo.dpToPx(context,25f),0)
        val a = context?.obtainStyledAttributes(attrs, R.styleable.RVPIndicato, 0, 0)
        mTabVisibleCount = a?.getInt(R.styleable.RVPIndicato_item_count, D_TAB_COUNT)!!
        mTextColorNormal = a?.getColor(R.styleable.RVPIndicato_text_color_normal,
                D_TEXT_COLOR_NORMAL)
        mTextColorHighlight = a?.getColor(
                R.styleable.RVPIndicato_text_color_hightlight,
                D_TEXT_COLOR_HIGHLIGHT)
        mTextSize = a?.getDimensionPixelSize(R.styleable.RVPIndicato_text_size,
                16)
        mIndicatorColor = a?.getColor(R.styleable.RVPIndicato_indicator_color,
                D_INDICATOR_COLOR)
        mIndicatorStyle = a?.getInt(R.styleable.RVPIndicato_indicator_style,
                DevicesInfo.STYLE_LINE)
        val drawable = a?.getDrawable(R.styleable.RVPIndicato_indicator_src)

        if (drawable != null) {
            if (drawable is BitmapDrawable) {
                mBitmap = drawable.getBitmap()
            } else if (drawable is NinePatchDrawable) {
                // .9图处理
                val bitmap = Bitmap.createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
                drawable.draw(canvas)
                mBitmap = bitmap

            }
        } else {
            mBitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.design)
        }

        a?.recycle()

        mPaint = Paint()
        mPaint?.isAntiAlias = true
        mPaint?.color = mIndicatorColor!!
        mPaint?.style = Paint.Style.FILL
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        when (mIndicatorStyle) {

            DevicesInfo.STYLE_LINE -> {
                mIndicatorWidth = w / mTabVisibleCount
                mIndicatorHeight = h / 10
                mTranslationX = 0F
                mRectF = Rect(0, 0, mIndicatorWidth, mIndicatorHeight)
            }
            DevicesInfo.STYLE_LINE, DevicesInfo.STYLE_BITMAP -> {

                mIndicatorWidth = w / mTabVisibleCount
                mIndicatorHeight = h
                mTranslationX = 0F
                mRectF = Rect(0, 0, mIndicatorWidth, mIndicatorHeight)
            }
            DevicesInfo.STYLE_TRIANGLE -> {

                mIndicatorWidth = (w / mTabVisibleCount * RADIO_TRIANGEL).toInt()// 1/6
                mIndicatorHeight = (mIndicatorWidth / 2 / Math.sqrt(2.0)).toInt()
                mTranslationX = 0F
            }
        }

//        initTabItem()
//
//        setHightLightTextView(mPosition)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas?.save()

        when (mIndicatorStyle) {

            DevicesInfo.STYLE_LINE, DevicesInfo.STYLE_SQUARE -> {
                canvas?.translate(mTranslationX, 0F)
                canvas?.drawBitmap(mBitmap, null, mRectF, mPaint)
            }
            DevicesInfo.STYLE_BITMAP -> {
                canvas?.translate(mTranslationX, (height - mIndicatorHeight).toFloat())
                canvas?.drawRect(mRectF, mPaint)
            }
            DevicesInfo.STYLE_TRIANGLE -> {
                canvas?.translate(mTranslationX, 0F)

                mPath = Path()

                val midOfTab = (width.toFloat()) / mTabVisibleCount / 2
                mPath?.moveTo(midOfTab -mIndicatorWidth / 2.toFloat(),height.toFloat()-mIndicatorHeight)
                mPath?.lineTo(midOfTab,(height).toFloat())
                mPath?.lineTo((midOfTab+(mIndicatorWidth/2)).toFloat(),height.toFloat() -mIndicatorHeight)
//
//                mPath?.moveTo(midOfTab.toFloat(), getHeight().toFloat()-mIndicatorHeight.toFloat())
//                mPath?.lineTo(midOfTab - mIndicatorWidth / 2.toFloat(), getHeight().toFloat())
//                mPath?.lineTo(midOfTab + mIndicatorWidth / 2.toFloat(), getHeight().toFloat())
                mPath?.close()
                canvas?.drawPath(mPath, mPaint)
            }
        }

        canvas?.restore()
    }

    fun onScoll(position: Int, offset: Float) {
        mTranslationX = width / RVPindicator.mTabVisibleCount * (position + offset)
        val tabWidth = width / RVPindicator.mTabVisibleCount
        if (offset > 0 && position >= (RVPindicator.mTabVisibleCount - 2)
                && childCount > RVPindicator.mTabVisibleCount && position < childCount - 2) {
            if (RVPindicator.mTabVisibleCount != 1) {
                val xValue = (position - (RVPindicator.mTabVisibleCount - 2) * tabWidth + (tabWidth * offset))
                this.scrollTo(xValue.toInt(), 0)
            } else {
                this.scrollTo(position * tabWidth + tabWidth * offset.toInt(), 0)
            }
        }
        invalidate()
    }

}