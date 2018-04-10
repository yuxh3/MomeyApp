package com.example.yuxinhua.momeyapp.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.example.yuxinhua.momeyapp.R

/**
 * Created by yuxh3
 * On 2018/4/8.
 * Email by 791285437@163.com
 */
class FindItemAdapterDecoration(context: Context, num:Int): RecyclerView.ItemDecoration(){

    private val ATTRS = intArrayOf(android.R.attr.listDivider)

    private var mDivider: Drawable? = null

    private val mDividerHeight = 5

    private var column_num = 0

    private var mPaintColor = Paint()
    init {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()
        column_num = num
        mPaintColor.color = context.resources.getColor(R.color.me_decoration_color)
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)

        drawHorizontal(c,parent)
        drawVertical(c,parent)
    }

    /**
     * 画竖线
     */
    private fun drawVertical(c: Canvas?, parent: RecyclerView?) {

        val childCount = parent?.childCount
        for (i in 0..childCount!!-1) {
            val child = parent?.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            var left = 0
            var right = 0

            var top = child.top - params.topMargin
            var bottom = child.bottom + params.bottomMargin + mDividerHeight

            if ((i % column_num) == 0){
                left = 0
                right = left + mDividerHeight
//                right = left + mDivider?.intrinsicWidth!!

                mDivider?.setBounds(left,top,right,bottom)
                c?.drawRect(left.toFloat(),top.toFloat(),right.toFloat(),bottom.toFloat(),mPaintColor)
                mDivider?.draw(c)

                left = child.right + params.rightMargin
                right = left + mDividerHeight
//                right = left + mDivider?.intrinsicWidth!!
            }else{
                left = child.right + params.rightMargin
//                right = left + mDivider?.intrinsicWidth!!
                right = left + mDividerHeight
            }
            mDivider?.setBounds(left,top,right,bottom)
            c?.drawRect(left.toFloat(),top.toFloat(),right.toFloat(),bottom.toFloat(),mPaintColor)
            mDivider?.draw(c)
        }
    }

    /**
     * 画横线
     */
    private fun drawHorizontal(c: Canvas?, parent: RecyclerView?) {

        val childCount = parent?.childCount
        for (i in 0..childCount!! -1){
            val child = parent?.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.left - params.leftMargin
//            val right = child.right + params.rightMargin + mDivider?.intrinsicWidth!!
            val right = child.right + params.rightMargin + mDividerHeight*2

            var top = 0
            var bottom = 0

            if (i / column_num == 0){
//                top = 0
//                bottom = top+mDividerHeight
////                bottom = top+mDivider?.intrinsicHeight!!
//                mDivider?.setBounds(left,top,right,bottom)
//                c?.drawRect(left.toFloat(),top.toFloat(),right.toFloat(),bottom.toFloat(),mPaintColor)
//                mDivider?.draw(c)

                top = child.bottom+params?.bottomMargin
                bottom = top+mDividerHeight*2
//                bottom = top+mDivider?.intrinsicHeight!!

                mDivider?.setBounds(left,top,right,bottom)
                c?.drawRect(left.toFloat(),top.toFloat(),right.toFloat(),bottom.toFloat(),mPaintColor)
                mDivider?.draw(c)
            }else{

                // 判断是否是最后一行
                val lastRow = (childCount - childCount % column_num)/column_num
                if (childCount % column_num == 0){

                    if ((lastRow-1) * column_num  <= i){
                        return
                    }
                }else {
                    if (lastRow * column_num  <= i){
                        return
                    }
                }
                top = child.bottom+params?.bottomMargin
//                bottom = top+mDivider?.intrinsicHeight!!
                bottom = top+mDividerHeight*2

                mDivider?.setBounds(left,top,right,bottom)
                c?.drawRect(left.toFloat(),top.toFloat(),right.toFloat(),bottom.toFloat(),mPaintColor)
                mDivider?.draw(c)
            }

        }

    }

    fun getSpanCount(parent: RecyclerView?):Int{
        var spanCount = -1

        val layoutManager = parent?.layoutManager
        if (layoutManager is GridLayoutManager){
            spanCount = layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager){
            spanCount = layoutManager.spanCount
        }

        return spanCount
    }

    /**
     * 是否是最后一个
     */
    private fun isLastColun(parent: RecyclerView?, pos:Int, spanCount:Int, childCount:Int):Boolean{
        val layoutManager = parent?.layoutManager
        if (layoutManager is GridLayoutManager){
            if ((pos+1) % spanCount == 0){
                return true
            }
        }else if (layoutManager is StaggeredGridLayoutManager){
            val orientation = (layoutManager as StaggeredGridLayoutManager).orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL){
                if ((pos + 1)% spanCount == 0){
                    return true
                }
            }else{
                val childCoun = childCount - childCount % spanCount

                if (pos >= childCoun) return true
            }
        }

        return false
    }

    /**
     *
     */
    private fun isLastRaw(parent: RecyclerView?, pos:Int, spanCount:Int, childCount:Int):Boolean{
        val layoutManager = parent?.layoutManager
        if (layoutManager is GridLayoutManager){
            val childCoun = childCount - childCount % spanCount

            if (pos >= childCoun) return true
        }else if (layoutManager is StaggeredGridLayoutManager){
            val orientation = (layoutManager as StaggeredGridLayoutManager).orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL){
                val childCoun = childCount - childCount % spanCount

                if (pos >= childCoun) return true
            }else{
                if ((pos + 1) % spanCount == 0) {
                    return true
                }
            }
        }

        return false
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
//        val spanCount = getSpanCount(parent)
//        val childCount = parent?.adapter?.itemCount
//        if (isLastColun(parent,parent?.getChildPosition(view)!!,spanCount,childCount!!)){
//            outRect?.set(0,0,mDivider?.intrinsicWidth!!,0)
//        }else if (isLastRaw(parent,parent?.getChildPosition(view)!!,spanCount,childCount!!)){
//            outRect?.set(0,0,0,mDivider?.intrinsicWidth!!)
//        }else{
//            outRect?.set(0,0,mDivider?.intrinsicWidth!!,mDivider?.intrinsicHeight!!)
//        }
    }

}