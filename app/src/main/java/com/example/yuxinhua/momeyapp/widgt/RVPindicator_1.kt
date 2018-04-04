package com.example.yuxinhua.momeyapp.widgt

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.NinePatchDrawable
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.utils.DevicesInfo.STYLE_BITMAP
import com.example.yuxinhua.momeyapp.utils.DevicesInfo.STYLE_LINE
import com.example.yuxinhua.momeyapp.utils.DevicesInfo.STYLE_SQUARE
import com.example.yuxinhua.momeyapp.utils.DevicesInfo.STYLE_TRIANGLE


/**
 * Created by yuxh3
 * On 2018/3/23.
 * Email by 791285437@163.com
 */
class RVPindicator_1 @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {

        /*
     * 系统默认:Tab数量
     */
        var D_TAB_COUNT = 2

        /*
         * 系统默认:文字正常时颜色
         */
        var D_TEXT_COLOR_NORMAL = Color.parseColor("#FF0000")

        /*
         * 系统默认:文字选中时颜色
         */
        var D_TEXT_COLOR_HIGHLIGHT = Color.parseColor("#FF0000")

        /*
         * 系统默认:指示器颜色
         */
        var D_INDICATOR_COLOR = Color.parseColor("#FFFFFF")
        /**
         * 可见tab数量
         */
        var mTabVisibleCount = D_TAB_COUNT

        /**
         * 文字正常时的颜色
         */
        var mTextColorNormal = D_TEXT_COLOR_NORMAL

        /**
         * 文字选中时的颜色
         */
        private var mTextColorHighlight = D_TEXT_COLOR_HIGHLIGHT

        /**
         * 指示器正常时的颜色
         */
        private var mIndicatorColor = D_INDICATOR_COLOR
    }


    /**
     * tab上的内容
     */
     var mTabTitles: List<String> = arrayListOf<String>()


    /**
     * 与之绑定的ViewPager
     */
    var mViewPager: ViewPager? = null

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
    private var mIndicatorStyle = STYLE_LINE

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
                STYLE_LINE)
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

            STYLE_LINE -> {
                mIndicatorWidth = w / mTabVisibleCount
                mIndicatorHeight = h / 10
                mTranslationX = 0F
                mRectF = Rect(0, 0, mIndicatorWidth, mIndicatorHeight)
            }
            STYLE_LINE, STYLE_BITMAP -> {

                mIndicatorWidth = w / mTabVisibleCount
                mIndicatorHeight = h
                mTranslationX = 0F
                mRectF = Rect(0, 0, mIndicatorWidth, mIndicatorHeight)
            }
            STYLE_TRIANGLE -> {

                mIndicatorWidth = (w / mTabVisibleCount * RADIO_TRIANGEL).toInt()// 1/6
                mIndicatorHeight = (mIndicatorWidth / 2 / Math.sqrt(2.0)).toInt()
                mTranslationX = 0F
            }
        }

        initTabItem()

        setHightLightTextView(mPosition)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas?.save()

        when (mIndicatorStyle) {

            STYLE_LINE, STYLE_SQUARE -> {
                canvas?.translate(mTranslationX, 0F)
                canvas?.drawBitmap(mBitmap, null, mRectF, mPaint)
            }
            STYLE_BITMAP -> {
                canvas?.translate(mTranslationX, (height - mIndicatorHeight).toFloat())
                canvas?.drawRect(mRectF, mPaint)
            }
            STYLE_TRIANGLE -> {
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


    fun setTabItemTitles(datas: ArrayList<String>) {
        this.mTabTitles = datas
    }

    fun setViewPager(viewPager: ViewPager, pos: Int) {
        this.mViewPager = viewPager
        mViewPager?.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                onPageChangeListener?.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                onScoll(position, positionOffset)
                onPageChangeListener?.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                setHightLightTextView(position)
                onPageChangeListener?.onPageSelected(position)
            }

        })

        mViewPager?.setCurrentItem(pos)
        mPosition = pos
    }

    fun initTabItem() {

        this.removeAllViews()


        for (title in mTabTitles!!) {
            addView(createTextView(title))
            measure(0, 0)
        }
        // 设置点击事件
        setItemClickEvent()
    }

    fun setItemClickEvent() {
        val mCount = childCount
        for (i in 0..mCount - 1) {
            val view = getChildAt(i)
            view.setOnClickListener {
                mViewPager?.setCurrentItem(i)
            }
        }
    }

    fun setHightLightTextView(mPosition: Int) {
        val mCount = childCount
        for (i in 0..mCount - 1) {
            val view = getChildAt(i)
            if (view is TextView) {
                if (i == mPosition) {
                    view.setTextColor(mTextColorHighlight)
                } else {
                    view.setTextColor(mTextColorNormal)
                }
            }
        }
    }

    fun onScoll(position: Int, offset: Float) {
        mTranslationX = width / mTabVisibleCount * (position + offset)
        val tabWidth = width / mTabVisibleCount
        if (offset > 0 && position >= (mTabVisibleCount - 2)
                && childCount > mTabVisibleCount && position < childCount - 2) {
            if (mTabVisibleCount != 1) {
                val xValue = (position - (mTabVisibleCount - 2) * tabWidth + (tabWidth * offset))
                this.scrollTo(xValue.toInt(), 0)
            } else {
                this.scrollTo(position * tabWidth + tabWidth * offset.toInt(), 0)
            }
        }
        invalidate()
    }

    fun createTextView(text: String): TextView {

        val tv :TextView= TextView(getContext())
        val lp = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        tv.layoutParams = lp
        lp.width = width / mTabVisibleCount
        tv.gravity = Gravity.CENTER
        tv.setTextColor(mTextColorNormal)
        tv.text = text
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize.toFloat())
        return tv
    }

    /**
     * 对外的ViewPager的回调接口
     * @author Ruffian
     */
    interface PageChangeListener {
        fun onPageScrolled(position: Int, positionOffset: Float,
                           positionOffsetPixels: Int)

        fun onPageSelected(position: Int)

        fun onPageScrollStateChanged(state: Int)
    }

    // 对外的ViewPager的回调接口
    private var onPageChangeListener: PageChangeListener? = null

    // 对外的ViewPager的回调接口的设置
    fun setOnPageChangeListener(pageChangeListener: PageChangeListener) {
        this.onPageChangeListener = pageChangeListener
    }
}