package com.example.yuxinhua.momeyapp.widgt

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.yuxinhua.momeyapp.R
import kotlinx.android.synthetic.main.head_text_layout.view.*

/**
 * Created by yuxh3
 * On 2018/3/23.
 * Email by 791285437@163.com
 */
class HeadTextView : LinearLayout {

    var mViblie = 0

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

        val view = LayoutInflater.from(context).inflate(R.layout.head_text_layout, this)
        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.head_text_style, 0, 0)

        mViblie = attributes.getIndex(R.styleable.head_text_style_ivsiblility)
        if (attributes.hasValue(R.styleable.head_text_style_leftIcon)) {
            iv_left.visibility = View.VISIBLE
            iv_left.setImageResource(attributes.getResourceId(R.styleable.head_text_style_leftIcon, 0))
        }
        if (attributes.hasValue(R.styleable.head_text_style_leftText)) {
            tv_left.setText(attributes.getString(R.styleable.head_text_style_leftText))
            tv_left.visibility = View.VISIBLE
        }
        if (attributes.hasValue(R.styleable.head_text_style_center)) {
            tv_center.visibility = View.VISIBLE
            tv_center.setText(attributes.getString(R.styleable.head_text_style_center))
        }
        if (attributes.hasValue(R.styleable.head_text_style_right)) {
            iv_right.visibility = View.VISIBLE
            iv_right.setImageResource(attributes.getResourceId(R.styleable.head_text_style_right, 0))
        }
        attributes.recycle()
    }


    fun setLeftImg(id: Int) {
        iv_left.visibility = mViblie
        iv_left.setImageResource(id)
    }

    fun setLeftImg(drawable: Drawable) {
        iv_left.visibility = mViblie
        iv_left.setImageDrawable(drawable)
    }

    fun setLeftText(msg: String) {
        iv_left.visibility = mViblie
        tv_left.setText(msg)
    }

    fun setCenterText(msg: String) {
        iv_left.visibility = mViblie
        tv_center.setText(msg)
    }

    fun setRightImg(drawable: Drawable) {
        iv_right.visibility = mViblie
        iv_right.setImageDrawable(drawable)
    }

    fun setRightImg(id: Int) {
        iv_right.visibility = mViblie
        iv_right.setImageResource(id)
    }


}