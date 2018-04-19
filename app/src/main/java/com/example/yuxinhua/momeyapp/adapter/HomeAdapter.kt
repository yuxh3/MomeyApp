package com.example.yuxinhua.momeyapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.yuxinhua.momeyapp.R
import com.squareup.picasso.Picasso

/**
 * Created by yuxh3
 * On 2018/3/22.
 * Email by 791285437@163.com
 */
class HomeAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    lateinit var mData:ArrayList<Int>
    fun setData(data:ArrayList<Int>){
        this.mData = data
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_adaper_layout,parent,false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is HomeViewHolder){
//            Picasso.with(context)
//                    .load(mData?.get(position))
//                    .placeholder(R.drawable.design)
//                    .into(holder.home_img)

            holder.home_img?.setImageResource(mData[position])

//            holder
        }
    }

    override fun getItemCount(): Int  = mData?.size?:0

    class HomeViewHolder :RecyclerView.ViewHolder{

        var home_img:ImageView? = null
        constructor(viewItem: View):super (viewItem){
            home_img = viewItem.findViewById<ImageView>(R.id.home_adapter_img)
        }

    }

}