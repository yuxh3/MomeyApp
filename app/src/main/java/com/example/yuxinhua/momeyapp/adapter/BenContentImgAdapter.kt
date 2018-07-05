package com.example.yuxinhua.momeyapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.notwork.`object`.BenLaiItemAllCategory
import com.example.notwork.`object`.BenLaiItemCate
import com.example.notwork.`object`.BenLaiItemData
import com.example.yuxinhua.momeyapp.R
import com.squareup.picasso.Picasso

/**
 * Created by yuxh3
 * On 2018/7/3.
 * Email by 791285437@163.com
 */
class BenContentImgAdapter(val content:Context, val data: BenLaiItemCate): RecyclerView.Adapter<BenContentImgAdapter.ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        Picasso.with(content).load(data.children.get(position).imgUrl).into(holder?.img)
        holder?.tv?.text = data.children.get(position).name
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BenContentImgAdapter.ViewHolder {
        var view = LayoutInflater.from(content).inflate(R.layout.ben_cont_img, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int  = data?.children.size?:0


    open class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val img:ImageView = itemView.findViewById(R.id.ben_img)
        val tv:TextView = itemView.findViewById(R.id.tv_item_name)

    }

}