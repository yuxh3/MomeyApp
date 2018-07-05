package com.example.yuxinhua.momeyapp.utils

import android.app.Activity
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.media.VolumeShaper
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import com.example.yuxinhua.momeyapp.ui.LoginActivity
import com.example.yuxinhua.momeyapp.ui.MyApplication
import com.example.yuxinhua.momeyapp.widgt.MyAppActivity
import java.text.DecimalFormat


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

    private var appDensity:Float=0.0f
    private var appScaledDensity:Float=0.0f
    private lateinit var appDisplayMetrics: DisplayMetrics

    fun setDensity(application : MyApplication){
        //获取application的DisplayMetrics
        appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (appDensity == 0.0f) {
            //初始化的时候赋值
            appDensity = appDisplayMetrics.density
            appScaledDensity = appDisplayMetrics.scaledDensity

            //添加字体变化的监听

            application.registerComponentCallbacks(object :ComponentCallbacks {
                override fun onConfigurationChanged(newConfig: Configuration?) {

                    //字体改变后,将appScaledDensity重新赋值
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appScaledDensity = application.getResources().getDisplayMetrics().scaledDensity
                    }
                }

                override fun onLowMemory() {
                }

            })
        }

        Log.i("yuxh3","appDensity-----"+appDensity+"-----appScaledDensity----"+appScaledDensity+"-----appDisplayMetrics---"+appDisplayMetrics)
    }

    fun setDefault(acitvity:Activity){
        setOrientation(acitvity, "360")
    }

    fun setOrientation(activity:Activity,orientation:String){
        setAppOrientation(activity, orientation)
    }

    fun setAppOrientation(activity:Activity,orientation:String){
        var targetDensity:Float=0.0f
        try {
            var division:Double
            //根据带入参数选择不同的适配方向
            if (orientation.equals("height")) {
                division = division(appDisplayMetrics.heightPixels.toDouble(), 667.0);
            } else {
                division = division(appDisplayMetrics.widthPixels.toDouble(), 360.0);
            }
            //由于手机的长宽不尽相同,肯定会有除不尽的情况,有失精度,所以在这里把所得结果做了一个保留两位小数的操作
             val df:DecimalFormat =  DecimalFormat("0.00")
             var s :String = df.format(division)
            targetDensity = s.toFloat()
        } catch ( e:NumberFormatException) {
            e.printStackTrace()
        }

        val targetScaledDensity:Float = targetDensity * (appScaledDensity / appDensity);
        val targetDensityDpi:Int = (160 * targetDensity).toInt()

        /**
         *
         * 最后在这里将修改过后的值赋给系统参数
         *
         * 只修改Activity的density值
         */

        val  activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity
        activityDisplayMetrics.scaledDensity = targetScaledDensity
        activityDisplayMetrics.densityDpi = targetDensityDpi


        Log.i("yuxh3","targetDensity-----"+targetDensity+"-----targetScaledDensity----"+targetScaledDensity+"-----targetDensityDpi---"+targetDensityDpi)

    }

    fun division(a:Double,b:Double):Double{
        var div:Double = 0.0
        if (b != 0.0){
            div = a/b
        }else{
            div = 0.0
        }
        return div
    }
}