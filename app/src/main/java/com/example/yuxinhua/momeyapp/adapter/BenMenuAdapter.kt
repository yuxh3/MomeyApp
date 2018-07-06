package com.example.yuxinhua.momeyapp.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.notwork.`object`.BenLaiItemData
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.ui.BenLaiActivity

/**
 * Created by yuxh3
 * On 2018/7/3.
 * Email by 791285437@163.com
 */
class BenMenuAdapter(val context:Context,val data:BenLaiItemData) : RecyclerView.Adapter<BenMenuAdapter.ViewHolder>() {

    lateinit var holde: ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

            var view = LayoutInflater.from(context).inflate(R.layout.ben_menu_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holde = holder!!
        holder?.tvContent?.text = data?.menu.get(position).name
        holder?.tvContent?.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                (context as BenLaiActivity).callBack(position)
//                holder.menuLay?.setBackgroundColor(Color.TRANSPARENT)
            }
        })

    }


    override fun getItemId(p0: Int): Long  = p0.toLong()

    override fun getItemCount(): Int = data?.menu?.size

    open class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var tvContent: TextView? = itemView?.findViewById(R.id.tv_menu)
        var menuLay : LinearLayout?= itemView?.findViewById(R.id.ll_menu)
    }
}