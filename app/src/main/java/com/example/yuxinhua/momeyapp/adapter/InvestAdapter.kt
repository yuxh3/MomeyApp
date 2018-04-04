package com.example.yuxinhua.momeyapp.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.data.InvestData
import com.example.yuxinhua.momeyapp.widgt.CircleProgressBar

/**
 * Created by yuxh3
 * On 2018/3/30.
 * Email by 791285437@163.com
 */
class InvestAdapter(val context: Context,mData:ArrayList<InvestData>):RecyclerView.Adapter<InvestAdapter.InvestHolder>(){


    var mData:ArrayList<InvestData>?= mData

    override fun getItemCount(): Int = mData?.size?:0

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): InvestHolder {

        return InvestHolder(LayoutInflater.from(context).inflate(R.layout.iveset_adapter_layout,parent,false))
    }

    override fun onBindViewHolder(holder: InvestHolder?, position: Int) {
        val bootomTxt = "投资中"

        holder?.mCardView?.radius = 10f
        holder?.mCardView?.cardElevation = 5f

        holder?.mCardView?.setContentPadding(3,3,8,8)

        holder?.mTvInverst?.setText(mData?.get(position)?.increase)
        holder?.mTvMoney?.setText(mData?.get(position)?.money)
        holder?.mTvMouth?.setText(mData?.get(position)?.mouth)
        holder?.mTitle?.setText(mData?.get(position)?.title)


        holder?.mInvesrtPro?.setProgress(mData?.get(position)?.progress?.toInt()!!)

        val srtLeft = holder?.mInvesrtPro?.getCountProgress().toString()+"%"+"\n"
        val srtRight = bootomTxt
        val spanSrtTop = SpannableString(srtLeft.plus(bootomTxt))
        spanSrtTop.setSpan(RelativeSizeSpan(0.7f),srtLeft.length,srtLeft.length+srtRight.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        holder?.mInvesrtPro?.mText = spanSrtTop.toString()
        holder?.mInvesrtPro?.textColor = Color.parseColor("#F75359")
        holder?.mInvesrtPro?.start()

    }


    inner class InvestHolder:RecyclerView.ViewHolder{

        var mTvInverst:TextView?= null
        var mTvMoney:TextView?= null
        var mTvMouth:TextView?= null
        var mTitle:TextView?= null
        var mCardView:CardView? = null
        var mInvesrtPro:CircleProgressBar? = null
        constructor(itemView: View?) : super(itemView){

            mTvInverst = itemView?.findViewById<View>(R.id.tv_inverst) as TextView?
            mTvMoney = itemView?.findViewById<View>(R.id.tv_money) as TextView?
            mTvMouth = itemView?.findViewById<View>(R.id.tv_mouth) as TextView?
            mTitle = itemView?.findViewById<View>(R.id.tv_title) as TextView?
            mInvesrtPro = itemView?.findViewById<View>(R.id.iverst_progress) as CircleProgressBar?

            mCardView = itemView?.findViewById<View>(R.id.card_view) as CardView
        }
    }

    open fun setData(data:ArrayList<InvestData>){
        this.mData = data
    }



}