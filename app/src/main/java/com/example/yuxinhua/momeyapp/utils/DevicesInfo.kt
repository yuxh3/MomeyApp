package com.example.yuxinhua.momeyapp.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.view.WindowManager


/**
 * Created by yuxh3
 * On 2018/3/18.
 * Email by 791285437@163.com
 */
object DevicesInfo{

    // 指示器风格-图标
     val STYLE_BITMAP = 0
    // 指示器风格-下划线
     val STYLE_LINE = 1
    // 指示器风格-方形背景
     val STYLE_SQUARE = 2
    // 指示器风格-三角形
     val STYLE_TRIANGLE = 3
    fun dpToPx(context:Context,dpValue:Float):Int{
        val scale = context.getResources().getDisplayMetrics().density
        return (dpValue * scale + 0.5f).toInt()
    }
    fun pxToDp(context:Context,dpValue:Float):Int{
        val scale = context.getResources().getDisplayMetrics().density
        return (dpValue / scale + 0.5f).toInt()
    }

    fun getWidth(context:Context):Int{

        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = wm.defaultDisplay.width
       return width
    }

    fun getHeight(context:Context):Int{
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val height = wm.defaultDisplay.height

        return height
    }

    /**
     * @param paint
     * @param str
     * @return 返回指定笔和指定字符串的长度
     */
    fun getFontlength(paint:Paint,str:String):Float{
        return paint.measureText(str)
    }
    /**
     * @return 返回指定笔的文字高度
     */
    fun getFontHeight(paint: Paint):Float{
        val fm = paint.fontMetrics
        return fm.descent - fm.ascent
    }

    /**
     * 返回指定笔离文字顶部的基准距离
     * 1.基准点是baseline
       2.ascent：是baseline之上至字符最高处的距离
       3.descent：是baseline之下至字符最低处的距离
       4.leading：是上一行字符的descent到下一行的ascent之间的距离,也就是相邻行间的空白距离
       5.top：是指的是最高字符到baseline的值,即ascent的最大值
       6.bottom：是指最低字符到baseline的值,即descent的最大值
     */
    fun getFontLeading(paint: Paint):Float{
        val fm = paint?.fontMetrics
        return fm.leading - fm.ascent
    }

    /**
     * 获取文字的中心线
     * 是以baseline为坐标(0.0)
     */
    fun getBaseLine(paint: Paint,rect: RectF):Float{
        val fm = paint?.fontMetrics
        return (rect.bottom+rect.top-(fm.bottom+fm.top))/2
    }

}