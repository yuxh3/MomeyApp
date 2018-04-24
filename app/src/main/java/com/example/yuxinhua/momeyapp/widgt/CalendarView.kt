package com.example.yuxinhua.momeyapp.widgt

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.data.DayFinish
import com.example.yuxinhua.momeyapp.utils.DevicesInfo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import android.text.method.TextKeyListener.clear







/**
 * Created by yuxh3
 * On 2018/4/20.
 * Email by 791285437@163.com
 */
class CalendarView:View{

    /** 各部分背景 */
    private var mBgMonth: Int = 0
    private var mBgWeek: Int = 0
    private var mBgDay: Int = 0
    private var mBgPre: Int = 0
    /** 标题的颜色、大小 */
    private var mTextColorMonth: Int = 0
    private var mTextSizeMonth: Float = 0f
    private var mMonthRowL: Int = 0
    var mMonthRowR: Int = 0
    private var mMonthRowSpac: Float = 0f
    private var mMonthSpac: Float = 0.toFloat()
    /** 星期的颜色、大小 */
    private var mTextColorWeek: Int = 0
    private var mSelectWeekTextColor: Int = 0
    private var mTextSizeWeek: Float = 0.toFloat()
    /** 日期文本的颜色、大小 */
    private var mTextColorDay: Int = 0
    private var mTextSizeDay: Float = 0.toFloat()
    /** 任务次数文本的颜色、大小 */
    private var mTextColorPreFinish: Int = 0
    private var mTextColorPreUnFinish: Int = 0
    private var mTextColorPreNull: Int = 0
    private var mTextSizePre: Float = 0.toFloat()
    /** 选中的文本的颜色 */
    private var mSelectTextColor: Int = 0
    /** 选中背景 */
    private var mSelectBg: Int = 0
    var mCurrentBg: Int = 0
    private var mSelectRadius: Float = 0.toFloat()
    var mCurrentBgStrokeWidth: Float = 0.toFloat()
    private var mCurrentBgDashPath: FloatArray? = null

    /** 行间距 */
    private var mLineSpac: Float = 0.toFloat()
    /** 字体上下间距 */
    private var mTextSpac: Float = 0.toFloat()

    private var mPaint: Paint? = null
    private var bgPaint: Paint? = null

    private var titleHeight: Float = 0.toFloat()
    var weekHeight: Float = 0.toFloat()
    var dayHeight: Float = 0.toFloat()
    var preHeight: Float = 0.toFloat()
    var oneHeight: Float = 0.toFloat()
    private var columnWidth: Int = 0 //每列宽度

    private var month: Date? = null //当前的月份
    private var isCurrentMonth: Boolean = false       //展示的月份是否是当前月
    private var currentDay: Int = 0
    var selectDay: Int = 0
    var lastSelectDay: Int = 0    //当前日期 、 选中的日期 、上一次选中的日期（避免造成重复回调请求）

    private var dayOfMonth: Int = 0    //月份天数
    private var firstIndex: Int = 0    //当月第一天位置索引
    private var todayWeekIndex: Int = 0//今天是星期几
    private var firstLineNum: Int = 0
    var lastLineNum: Int = 0 //第一行、最后一行能展示多少日期
    private var lineNum: Int = 0      //日期行数
    private var WEEK_STR = arrayOf("日", "一", "二", "三", "四", "五", "六")

    init {
    }
    constructor(context: Context?) : super(context){
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        val a = context?.obtainStyledAttributes(attrs, R.styleable.Calendar_View)

        mBgMonth = a?.getColor(R.styleable.Calendar_View_mBgMonth,Color.TRANSPARENT)!!
        mBgWeek = a?.getColor(R.styleable.Calendar_View_mBgWeek,Color.TRANSPARENT)!!
        mBgDay = a?.getColor(R.styleable.Calendar_View_mBgDay,Color.TRANSPARENT)!!
        mBgPre = a?.getColor(R.styleable.Calendar_View_mBgPre,Color.TRANSPARENT)!!

        mMonthRowL = a?.getResourceId(R.styleable.Calendar_View_mMonthRowL,R.mipmap.custom_calendar_row_left)
        mMonthRowR = a?.getResourceId(R.styleable.Calendar_View_mMonthRowR,R.mipmap.custom_calendar_row_right)

        mMonthRowSpac = a?.getDimension(R.styleable.Calendar_View_mMonthRowSpac,20F)
        mTextColorMonth = a?.getColor(R.styleable.Calendar_View_mTextColorMonth,Color.BLACK)
        mTextSizeMonth = a.getDimension(R.styleable.Calendar_View_mTextSizeMonth, 100f)
        mMonthSpac = a.getDimension(R.styleable.Calendar_View_mMonthSpac, 20f)
        mTextColorWeek = a.getColor(R.styleable.Calendar_View_mTextColorWeek, Color.BLACK)
        mSelectWeekTextColor = a.getColor(R.styleable.Calendar_View_mSelectWeekTextColor, Color.BLACK)

        mTextSizeWeek = a.getDimension(R.styleable.Calendar_View_mTextSizeWeek, 70f)
        mTextColorDay = a.getColor(R.styleable.Calendar_View_mTextColorDay, Color.GRAY)
        mTextSizeDay = a.getDimension(R.styleable.Calendar_View_mTextSizeDay, 70f)
        mTextColorPreFinish = a.getColor(R.styleable.Calendar_View_mTextColorPreFinish, Color.BLUE)
        mTextColorPreUnFinish = a.getColor(R.styleable.Calendar_View_mTextColorPreUnFinish, Color.BLUE)
        mTextColorPreNull  = a.getColor(R.styleable.Calendar_View_mTextColorPreNull, Color.BLUE)
        mTextSizePre = a.getDimension(R.styleable.Calendar_View_mTextSizePre, 40f)
        mSelectTextColor = a.getColor(R.styleable.Calendar_View_mSelectTextColor, Color.YELLOW)
        mCurrentBg = a.getColor(R.styleable.Calendar_View_mCurrentBg, Color.GRAY)

        try{
            val dashPathId = a?.getResourceId(R.styleable.Calendar_View_mCurrentBgDashPath,R.array.customCalendar_currentDay_bg_DashPath)
            val array = resources.getIntArray(dashPathId)
            mCurrentBgDashPath = kotlin.FloatArray(array.size)

            for(i in 0..array.size){
                mCurrentBgDashPath!![i] = array[i].toFloat()
            }
        }catch (e:Exception){
            e.printStackTrace()
            mCurrentBgDashPath = floatArrayOf(2f,3f,2f,3f)
        }

        mSelectBg = a.getColor(R.styleable.Calendar_View_mSelectBg, Color.YELLOW);
        mSelectRadius = a.getDimension(R.styleable.Calendar_View_mSelectRadius, 20f)
        mCurrentBgStrokeWidth = a.getDimension(R.styleable.Calendar_View_mCurrentBgStrokeWidth, 5f)
        mLineSpac = a.getDimension(R.styleable.Calendar_View_mLineSpac, 20f)
        mTextSpac = a.getDimension(R.styleable.Calendar_View_mTextSpac, 20f)
        a.recycle()  //注意回收

        initCompute()
    }

    private var map:HashMap<Int,DayFinish>?= null
    fun initCompute(){
        mPaint = Paint()
        bgPaint = Paint()

        mPaint?.isAntiAlias = true
        bgPaint?.isAntiAlias = true

        map = HashMap<Int,DayFinish>()

        mPaint?.textSize = mTextSizeMonth
        //标题的高度
        titleHeight = DevicesInfo.getFontHeight(mPaint!!)

        //星期高度
        mPaint?.textSize = mTextSizeWeek
        weekHeight = DevicesInfo.getFontHeight(mPaint!!)

        //日前高度
        mPaint?.textSize = mTextSizeDay
        dayHeight = DevicesInfo.getFontHeight(mPaint!!)

        //次数字体高度
        mPaint?.textSize = mTextSizePre
        preHeight = DevicesInfo?.getFontHeight(mPaint!!)

        //每行高度 = 行间距 + 日期字体高度 + 字间距 + 次数字体高度
        oneHeight = mLineSpac + dayHeight + mTextSpac + preHeight

        val cDateStr = getMonthSrt(Date())

        setMonth(cDateStr)

    }

    fun setMonth(mont:String){
        month = srt2Date(mont)

        val calendar = Calendar.getInstance()
        calendar.time = Date()

        //获取今天多少号
        currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        //星期几
        todayWeekIndex = calendar.get(Calendar.DAY_OF_WEEK) -1

        val cM = srt2Date(getMonthSrt(Date()))

        if (cM.time == month?.time){
            isCurrentMonth = true
            selectDay = currentDay
        }else{
            isCurrentMonth = false
            selectDay = 0
        }

        calendar.time = month
        dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)//得到当前月份的最大天数
        //第一行1号显示在什么位置（星期几）
        firstIndex = calendar.get(Calendar.DAY_OF_WEEK) -1
        lineNum = 1
        //第一行能展示的天数
        firstLineNum = 7 - firstIndex
        lastLineNum = 0
        var shengyu = dayOfMonth - firstLineNum

        while (shengyu >7){
            lineNum ++
            shengyu -=7
        }
        if (shengyu >0){
            lineNum ++
            lastLineNum = shengyu
        }

    }

    /**
     * 获取上一个月最大多少天
     */
    fun getMonthAgo(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.MONTH, -1)

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)//得到当前月份的最大天数
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        columnWidth = widthSize / 7
        //高度 = 标题高 + 星期高 + 日期行数*每行高度
        val height = titleHeight*3/2 + weekHeight*2 +(lineNum * oneHeight)

        //宽度是使用默认的最小的宽度为宽度
        setMeasuredDimension(getDefaultSize(suggestedMinimumWidth,widthMeasureSpec),height.toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        drawMonth(canvas)
        drawWeek(canvas)
        drawDayAndPre(canvas)
    }

    private var rowLStart = -1
    private var rowRStart = -1
    private var rowWidth = -1

    fun drawMonth(canvas: Canvas?){
        //背景
        bgPaint?.color = mBgMonth
        val rect = RectF(0f,0f,width.toFloat(),titleHeight*3/2)
        canvas?.drawRect(rect,bgPaint)
        // 绘制月份
        mPaint?.textSize = mTextSizeMonth
        mPaint?.color = mTextColorMonth
        val textLen = DevicesInfo.getFontlength(mPaint!!,getMonthSrt(month!!))
        val textStart = (width - textLen) / 2
        canvas?.drawText(getMonthSrt(month!!),textStart,DevicesInfo.getBaseLine(mPaint!!,rect),mPaint)

        //绘制左右箭头
        val bitmap = BitmapFactory.decodeResource(resources,mMonthRowL)
        val h = bitmap.height
        rowWidth = bitmap.width

        rowLStart = (textStart - 2*mMonthRowSpac - rowWidth).toInt()
        canvas?.drawBitmap(bitmap,rowLStart+mMonthRowSpac,rect.centerY()-h/2, mPaint)

        //绘制右边的箭头
        val bitmapR = BitmapFactory.decodeResource(resources,mMonthRowR)
        rowRStart = (textStart + textLen).toInt()
        canvas?.drawBitmap(bitmapR,rowRStart + mMonthRowSpac,rect.centerY()-h/2, mPaint)

    }

    /**
     * 绘制星期
     */
    fun drawWeek(canvas: Canvas?){

        bgPaint?.color = mBgWeek
        val rect = RectF(0f,titleHeight*3/2,width.toFloat(),titleHeight*3/2+weekHeight*2)
        canvas?.drawRect(rect,bgPaint)

        //绘制星期 7天
        mPaint?.textSize = mTextSizeWeek

        for (i in 0.. WEEK_STR.size -1){
            if (todayWeekIndex == i && isCurrentMonth){
                mPaint?.color = mSelectWeekTextColor
            }else{
                mPaint?.color = mTextColorWeek
            }
            val len = DevicesInfo.getFontlength(mPaint!!,WEEK_STR[i])
            val x = i * columnWidth +(columnWidth -len)/2
            canvas?.drawText(WEEK_STR[i],x,DevicesInfo.getBaseLine(mPaint!!,rect),mPaint)
        }
    }

    /**
     * 绘制日期和次数
     */
    fun drawDayAndPre(canvas: Canvas?){
        var top = titleHeight*3/2+ weekHeight*2

        for (line in 0..lineNum -1){
            if (line == 0){
                drawDayAndPre(canvas,top,7,0,firstIndex)
            }else if (line == lineNum-1){//最后一行
                top += oneHeight
                drawDayAndPre(canvas,top,7,firstLineNum+(line -1)*7,0)
            }else{
                top += oneHeight
                drawDayAndPre(canvas,top,7,firstLineNum+(line -1)*7,0)
            }
        }
    }

    /**
     * 绘制某一行的日期
     * @param canvas
     * @param top 顶部坐标
     * @param count 此行需要绘制的日期数量（不一定都是7天）
     * @param overDay 已经绘制过的日期，从overDay+1开始绘制
     * @param startIndex 此行第一个日期的星期索引
     */
    fun drawDayAndPre(canvas: Canvas?,top:Float,count:Int,overDay:Int,startIndex:Int){
        val topPre = top+mLineSpac+dayHeight
        bgPaint?.color = mBgDay
        var rect = RectF(0.toFloat(),top,width.toFloat(),topPre)
        canvas?.drawRect(rect,bgPaint)

        bgPaint?.color = mBgPre
        rect = RectF(0.toFloat(),topPre,width.toFloat(),topPre+mTextSpac+dayHeight)
        canvas?.drawRect(rect,bgPaint)

        mPaint?.textSize = mTextSizeDay
        val dayTextLeading = DevicesInfo.getFontLeading(mPaint!!)
        mPaint?.textSize = mTextSizePre
        val preTextLeading = DevicesInfo.getFontLeading(mPaint!!)


        if (startIndex>0){
            val agoDayAndMonth = getMonthAgo(Date())
            var day = agoDayAndMonth
            for (y  in startIndex -1 downTo 0){
               val left = (y)*columnWidth
                mPaint?.textSize = mTextSizeDay
                mPaint?.color = Color.parseColor("#267A7E83")
                var len = DevicesInfo.getFontlength(mPaint!!,day.toString())
                var x = left + (columnWidth -len)/2

                 canvas?.drawText(day.toString(), x, top + mLineSpac + dayTextLeading, mPaint)//绘制文字

                day = day - 1
            }
        }
        for (i in 0..count - 1){
            var left = (startIndex +i)*columnWidth

            var day = (overDay + i + 1)

            mPaint?.textSize = mTextSizeDay

            if (isCurrentMonth && currentDay == day){
                mPaint?.color = mTextColorDay
                bgPaint?.color = Color.parseColor("#fe7f5c")
                bgPaint?.style = Paint.Style.STROKE

                /** mCurrentBgDashPath--(8,8,8,8),前者表示先画8长度不透明，然后8透明，再8不透明-》重复）
                 * phase --> (8)  表示偏移8长度
                 */
                val effect = DashPathEffect(mCurrentBgDashPath,1f)
                bgPaint?.pathEffect = effect
                bgPaint?.strokeWidth = mCurrentBgStrokeWidth

                //绘制空心圆背景
                canvas?.drawCircle((left+ columnWidth/2).toFloat(),top+mLineSpac+dayHeight/2,mSelectRadius - mCurrentBgStrokeWidth,bgPaint)
            }

            bgPaint?.setPathEffect(null)
            bgPaint?.strokeWidth = 0f
            bgPaint?.style = Paint.Style.FILL

            //选中的日期，如果是本月，选中日期正好是当天日期，下面的背景会覆盖上面绘制的虚线背景
            if (selectDay == day){
                mPaint?.color = mSelectTextColor
                bgPaint?.color = Color.parseColor("#fe7f5c")
                //绘制橙色圆背景，参数一是中心点的x轴，参数二是中心点的y轴，参数三是半径，参数四是paint对象；
                canvas?.drawCircle(left+columnWidth/2.toFloat(), top + mLineSpac +dayHeight/2, mSelectRadius, bgPaint)
            }else{
                mPaint?.color = mTextColorDay
            }

            var len = DevicesInfo.getFontlength(mPaint!!,day.toString())
            var x = left + (columnWidth -len)/2

            if (day>dayOfMonth){

                day -= dayOfMonth
//                mPaint?.textSize = 10f
                mPaint?.color = Color.parseColor("#267A7E83")
            }
                canvas?.drawText(day.toString(), x, top + mLineSpac + dayTextLeading, mPaint)//绘制文字


            //绘制次数
//            mPaint?.textSize = mTextSizePre
//            val finsh = map?.get(day)

//            var preStr = "0/0"
//            if (isCurrentMonth){
//                if (day>currentDay){
//                    mPaint?.color  = mTextColorPreNull
//                }else if (finsh != null){
//                    if (finsh.finish > finsh.all){
//                        mPaint?.color = mTextColorPreFinish
//                    }else{
//                        mPaint?.color = mTextColorPreUnFinish
//                    }
//
//                    preStr = finsh.finish.toString()+"/"+finsh.all
//                }else{
//                    mPaint?.color = mTextColorPreNull
//                }
//            }else{
//                if (finsh != null){
//                    if (finsh.finish >= finsh.all){
//                        mPaint?.color = mTextColorPreFinish
//                    }else{
//                        mPaint?.color = mTextColorPreUnFinish
//                    }
//                    preStr = finsh.finish.toString()+"/"+finsh.all
//                }else{
//                    mPaint?.color = mTextColorPreNull
//                }
//            }

//            len = DevicesInfo.getFontlength(mPaint!!,preStr)
//
//            x = left + (columnWidth -len)/2
//            canvas?.drawText(preStr,x,topPre+mTextSpac+preTextLeading,mPaint)

        }
    }
    fun getMonthSrt(date:Date):String{
        val df = SimpleDateFormat("yyyy年MM月")
        return df.format(date)
    }

    private fun srt2Date(str:String):Date{
        val df = SimpleDateFormat("yyyy年MM月")
        return df.parse(str)
    }

    //-------------事件的处理----------------//

    private val focusPoint = PointF()
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action and MotionEvent.ACTION_MASK
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                focusPoint.set(event.x, event.y)
                touchFocusMove(focusPoint, false)
            }
            MotionEvent.ACTION_MOVE -> {
                focusPoint.set(event.x, event.y)
                touchFocusMove(focusPoint, false)
            }
            MotionEvent.ACTION_OUTSIDE, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                focusPoint.set(event.x, event.y)
                touchFocusMove(focusPoint, true)
            }
        }
        return true
    }

    private fun touchFocusMove(point: PointF, eventEnd: Boolean) {
        if (point.y<=titleHeight*3/2){
            if (eventEnd && listener != null){
                if (point.x >= rowLStart && point.x<= rowLStart +2*mMonthRowSpac+rowWidth){
                    listener?.onLeftRowClick()
                }else if (point.x>rowRStart && point.x<= (rowRStart+2*mMonthRowSpac+rowWidth)){
                    listener?.onRightRowClick()
                }else if (point.x>rowLStart && point.x<rowRStart){
                    listener?.onTitleClick(getMonthSrt(month!!),month!!)
                }
            }
        }else if (point.y <= (titleHeight*3/2 + weekHeight*2)){
            if (eventEnd  && listener != null){
                var xIndex:Int = (point.x / columnWidth).toInt()
                if (point.x / columnWidth - xIndex>0){
                    xIndex += 1
                }
                listener?.onWeekClick(xIndex -1,WEEK_STR[xIndex -1])
            }
        }else{
            touchDay(point,eventEnd)
        }
    }

    //控制事件是否响应
    private var responseWhenEnd = false
    // 事件点在 日期区域 范围内
    fun touchDay(point: PointF,eventEnd: Boolean){
        var availability = false
        var top = titleHeight*3/2 +weekHeight*2 + oneHeight
        var foucsLine = 1
        while (foucsLine <=lineNum){
            if (top >= point.y){
                availability = true
                break
            }
            top += oneHeight
            foucsLine++
        }
        if (availability){
            var xIndex:Int = (point.x / columnWidth).toInt()
            Log.i("yuxh3","xIndex--------00000------->"+xIndex)
            if ((point.x /columnWidth -xIndex)>0){
                xIndex += 1
            }

            Log.i("yuxh3","xIndex---------11111------>"+xIndex)
            if (xIndex <=0){
                xIndex = 1
            }
            if (xIndex >7)
                xIndex =7
            if (foucsLine ==1){
                if (xIndex <= firstIndex){
                    setSelectedDay(selectDay,true)
                }else{
                    setSelectedDay(xIndex - firstIndex,eventEnd)
                }
            }else if (foucsLine == lineNum){
                if (xIndex >lastLineNum){
                    setSelectedDay(selectDay,true)
                }else{
                    setSelectedDay(firstLineNum + (foucsLine - 2) * 7 + xIndex,eventEnd)
                }
            }else{
                setSelectedDay(firstLineNum + (foucsLine - 2) * 7 + xIndex,eventEnd)
            }
        }else{
            //超出日期区域后，视为事件结束，响应最后一个选择的日期回调
            setSelectedDay(selectDay,true)
        }
    }

    //设置选中的日期
    fun setSelectedDay(day: Int,eventEnd: Boolean){
        selectDay = day
        invalidate()
        if (listener != null && eventEnd && responseWhenEnd && lastSelectDay!= selectDay){
            lastSelectDay = selectDay
            listener?.onDayClick(selectDay,getMonthSrt(Date())+selectDay+"日")
        }
        responseWhenEnd = !eventEnd
    }

    override fun invalidate() {
        requestLayout()
        super.invalidate()
    }
    private var listener: onClickListener? = null
    fun setOnClickListener(listener: onClickListener) {
        this.listener = listener
    }

    public interface onClickListener {

        fun onLeftRowClick()
        fun onRightRowClick()
        fun onTitleClick(monthStr: String, month: Date)
        fun onWeekClick(weekIndex: Int, weekStr: String)
//        fun onDayClick(day: Int, dayStr: String, finish: DayFinish)
        fun onDayClick(day: Int, dayStr: String)
    }

    /**
     * 改变月份
     */
    open fun monthChange(change:Int){
        val calendar = Calendar.getInstance()
        calendar.time = month
        calendar.add(Calendar.MONTH, change)
        setMonth(getMonthSrt(calendar.time))
        map?.clear()
        invalidate()
    }
}