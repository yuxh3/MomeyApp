package com.example.yuxinhua.momeyapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.data.MeData
import android.widget.TextView



/**
 * Created by yuxh3
 * On 2018/4/26.
 * Email by 791285437@163.com
 */
class GridViewAdapter(val context: Context,data:ArrayList<MeData>):BaseAdapter(){

    private var mData:ArrayList<MeData>? = data
//    fun setData(data:ArrayList<MeData>){
//
//        this.mData = data
//    }
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        var viewHolder:ViewHolder
        var view:View
        if (p1 == null){
            view = LayoutInflater.from(context).inflate(R.layout.me_adapter,p2,false)
            viewHolder = ViewHolder()
            viewHolder.mImg = view?.findViewById(R.id.iv_img)
            viewHolder.tvContent = view?.findViewById(R.id.tv_me_title)
            view.tag = viewHolder
        }else{
            view = p1
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.tvContent?.text= mData?.get(p0)?.message
        viewHolder.mImg?.setImageResource(mData?.get(p0)?.id!!)
        return view
    }

    override fun getItem(p0: Int): Any  = p0

    override fun getItemId(p0: Int): Long  = p0.toLong()

    override fun getCount(): Int  = mData?.size?:0

    internal class ViewHolder {
        var tvContent: TextView? = null
        var mImg:ImageView? = null
    }
}