package com.example.yuxinhua.momeyapp.widgt

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.yuxinhua.momeyapp.R
import kotlinx.android.synthetic.main.recent_loading_view.view.*

/**
 * Created by yuxh3
 * On 2018/3/27.
 * Email by 791285437@163.com
 */
class RecentLoadingView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
):FrameLayout(context, attrs, defStyleAttr){

    private var mResDrawable = arrayListOf<Int>(R.drawable.p1,R.drawable.p3,R.drawable.p5,R.drawable.p7)
    private var mIndex:Int = -1
    private var mSkip = true
    private val MAX_HEIGHT = 80
    private val DURATION_DEFAULT = 430
    private var DURATION = DURATION_DEFAULT

    lateinit var rotateAnimationLeft: RotateAnimation
    lateinit var rotateAnimationRight:RotateAnimation
    lateinit var translateAnimation:TranslateAnimation
    lateinit var backAnimation:TranslateAnimation

    lateinit var animationSetLeft2:AnimationSet
    lateinit var animationSetLeft:AnimationSet
    lateinit var animationSetRight2:AnimationSet
    lateinit var animationSetRight:AnimationSet

    private var mViewAnimEndListener:OnViewAnimEndListener? = null
    private var isLeftRotate = true

    init {
        init()
    }
    fun init(){
        val view = LayoutInflater.from(context).inflate(R.layout.recent_loading_view,this)
        iv_loading.setImageResource(mResDrawable[1])
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(0,MAX_HEIGHT,0,0)
        tv_loading.layoutParams  = layoutParams
    }

    open fun setOnViewAnimEndListener(mViewAnimEndListener: OnViewAnimEndListener){
        this.mViewAnimEndListener = mViewAnimEndListener
    }

    /**
     * 改变图标
     */
    fun changeIcon(){
        iv_loading.clearAnimation()
        if (mSkip){
            mIndex = 2
            mSkip = false
        }else{
            mIndex = if(mIndex == mResDrawable.size -1)0 else mIndex+1
        }
        isLeftRotate = !isLeftRotate
        iv_loading.setImageResource(mResDrawable[mIndex])
    }
    open interface OnViewAnimEndListener{
        fun onDropDown()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        loadAnim()
    }
    fun loadAnim(){
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.KITKAT){
            DURATION = DURATION_DEFAULT
        }else{
            DURATION = DURATION_DEFAULT*4/5
        }
        translateAnimation = TranslateAnimation(0f,0f,0f,MAX_HEIGHT+20.toFloat())
        translateAnimation.duration = DURATION.toLong()
        translateAnimation.fillAfter = true

        backAnimation = TranslateAnimation(0f,0f,MAX_HEIGHT+20.toFloat(),0f)
        backAnimation.duration = DURATION.toLong()
        backAnimation.fillAfter = true

        rotateAnimationLeft = RotateAnimation(30f,-30f,Animation.RELATIVE_TO_SELF,0.5f
        ,Animation.RELATIVE_TO_SELF,0.5f)
        rotateAnimationLeft.repeatCount = 0
        rotateAnimationLeft.setInterpolator(LinearInterpolator())
        rotateAnimationLeft.fillAfter = true
        rotateAnimationLeft.duration = DURATION.toLong()

        rotateAnimationRight = RotateAnimation(-30f,30f,Animation.RELATIVE_TO_SELF,0.5f
                ,Animation.RELATIVE_TO_SELF,0.5f)
        rotateAnimationRight.repeatCount = 0
        rotateAnimationRight.setInterpolator(LinearInterpolator())
        rotateAnimationRight.fillAfter = true
        rotateAnimationRight.duration = DURATION.toLong()

        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.KITKAT){
            animationSetRight = AnimationSet(true)
            animationSetRight.addAnimation(rotateAnimationRight)
            animationSetRight.addAnimation(translateAnimation)
            animationSetRight.duration = DURATION.toLong()

            animationSetRight2 = AnimationSet(true)
            animationSetRight2.addAnimation(rotateAnimationLeft)
            animationSetRight2.addAnimation(backAnimation)
            animationSetRight2.duration = DURATION.toLong()

            animationSetLeft = AnimationSet(true)
            animationSetLeft.addAnimation(rotateAnimationRight)
            animationSetLeft.addAnimation(translateAnimation)
            animationSetLeft.duration = DURATION.toLong()

            animationSetLeft2 = AnimationSet(true)
            animationSetLeft2.addAnimation(rotateAnimationLeft)
            animationSetLeft2.addAnimation(backAnimation)
            animationSetLeft2.duration = DURATION.toLong()
        }else{
            animationSetRight = AnimationSet(true)
            animationSetRight.addAnimation(rotateAnimationLeft)
            animationSetRight.addAnimation(translateAnimation)
            animationSetRight.duration = DURATION.toLong()

            animationSetRight2 = AnimationSet(true)
            animationSetRight2.addAnimation(rotateAnimationRight)
            animationSetRight2.addAnimation(backAnimation)
            animationSetRight2.duration = DURATION.toLong()

            animationSetLeft = AnimationSet(true)
            animationSetLeft.addAnimation(rotateAnimationRight)
            animationSetLeft.addAnimation(translateAnimation)
            animationSetLeft.duration = DURATION.toLong()

            animationSetLeft2 = AnimationSet(true)
            animationSetLeft2.addAnimation(rotateAnimationLeft)
            animationSetLeft2.addAnimation(backAnimation)
            animationSetLeft2.duration = DURATION.toLong()
        }

        translateAnimation.setAnimationListener(translateListener)
        backAnimation.setAnimationListener(backListener)

        animationSetLeft2.setInterpolator(context, android.R.anim.decelerate_interpolator)
        animationSetRight2.setInterpolator(context, android.R.anim.decelerate_interpolator)

        animationSetLeft.setInterpolator(context, android.R.anim.accelerate_interpolator)
        animationSetRight.setInterpolator(context, android.R.anim.accelerate_interpolator)

        iv_loading.animation = animationSetLeft2
        animationSetLeft2.start()

    }

    private val translateListener = object :Animation.AnimationListener{

        override fun onAnimationRepeat(p0: Animation?) {
        }

        override fun onAnimationEnd(p0: Animation?) {
            tv_loading.startAnim()
            changeIcon()
            if (isLeftRotate){
                iv_loading.animation = animationSetLeft2
                animationSetLeft2.start()
            }else{
                iv_loading.animation = animationSetRight2
                animationSetRight2.start()
            }
        }

        override fun onAnimationStart(p0: Animation?) {
        }

    }

    private val backListener = object :Animation.AnimationListener{

        override fun onAnimationRepeat(p0: Animation?) {
        }

        override fun onAnimationEnd(p0: Animation?) {
            if (isLeftRotate){
                iv_loading.animation = animationSetLeft
                animationSetLeft.start()
            }else{
                iv_loading.animation = animationSetRight
                animationSetRight.start()
            }
        }

        override fun onAnimationStart(p0: Animation?) {
        }

    }

}