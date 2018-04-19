package com.example.yuxinhua.momeyapp.utils

import android.content.Context
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

}