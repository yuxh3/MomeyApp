package com.example.yuxinhua.momeyapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.data.MeData

/**
 * Created by yuxh3
 * On 2018/4/8.
 * Email by 791285437@163.com
 */
class MeAdapter(val context: Context): RecyclerView.Adapter<MeAdapter.MeViewHolder>() {

    private var mData:ArrayList<MeData>? = null
    fun setData(data:ArrayList<MeData>){

        this.mData = data
    }
    override fun onBindViewHolder(holder: MeAdapter.MeViewHolder?, position: Int) {

        holder?.mIvImg?.setImageResource(mData?.get(position)?.id!!)
        holder?.mTv?.text = mData?.get(position)?.message
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MeAdapter.MeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.me_adapter,parent,false)
        return MeViewHolder(view)
    }

    override fun getItemCount(): Int = mData?.size?:0

    class MeViewHolder:RecyclerView.ViewHolder{

         var mIvImg:ImageView? = null
         var mTv:TextView? = null
        constructor(itemView: View?) : super(itemView){

            mIvImg  = itemView?.findViewById<ImageView>(R.id.iv_img)
            mTv  = itemView?.findViewById<TextView>(R.id.tv_me_title)
        }
    }

}