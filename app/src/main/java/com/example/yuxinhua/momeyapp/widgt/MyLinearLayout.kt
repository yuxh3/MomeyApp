package com.example.yuxinhua.momeyapp.widgt

import android.content.Context
import android.graphics.Color
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.utils.DevicesInfo
import kotlinx.android.synthetic.main.my_layout.view.*

/**
 * Created by yuxh3
 * On 2018/4/17.
 * Email by 791285437@163.com
 */
class MyLinearLayout: LinearLayout {

    constructor(context: Context?) : super(context){

        initView(context)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        initView(context)
    }

    /**
     * viewPage初始下标
    */
    private var mPosition = 0


    /**
     * tab上的内容
     */
    var mTabTitles: List<String> = arrayListOf<String>()


    /**
     * 与之绑定的ViewPager
     */
    var mViewPager: ViewPager? = null


    /**
     * 系统默认:文字正常时颜色
     */
    var D_TEXT_COLOR_NORMAL = Color.parseColor("#000000")

    /*
     * 系统默认:文字选中时颜色
     */
    var D_TEXT_COLOR_HIGHLIGHT = Color.parseColor("#FFEAC04B")


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
     * 手指滑动时的偏移量
     */
    private var mTranslationX: Float = 0.toFloat()

    init {
        this.orientation = VERTICAL
    }

    fun initView(context: Context?) {
        val view = LayoutInflater.from(context).inflate(R.layout.my_layout,this)

    }

    fun setTabItemTitles(datas: ArrayList<String>) {
        this.mTabTitles = datas

        tv_title_1.setText(datas.get(0))
        tv_title_2.setText(datas.get(1))

        measure(0,0)
    }

    fun setViewPager(viewPager: ViewPager, pos: Int) {
        this.mViewPager = viewPager
        mViewPager?.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                onPageChangeListener?.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                log_indicator.onScoll(position, positionOffset)
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

        setItemClickEvent()
    }

    fun setItemClickEvent() {
        val mCount = ((getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout).childCount
        for (i in 0..mCount - 1) {
            val view = ((getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(i)
            view.setOnClickListener {
                mViewPager?.setCurrentItem(i)
            }
        }
    }

    fun setHightLightTextView(mPosition: Int) {
        val mCount = ((getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout).childCount
        for (i in 0..mCount - 1) {
            val view = ((getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(i)
            if (view is TextView) {
                if (i == mPosition) {
                    view.setTextColor(mTextColorHighlight)
                    (view as TextView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                } else {
                    view.setTextColor(mTextColorNormal)
                    (view as TextView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
                }
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        initTabItem()

        setHightLightTextView(mPosition)
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