package com.example.yuxinhua.momeyapp.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.ui.BaseActivity
import com.example.yuxinhua.momeyapp.ui.LoginActivity
import com.example.yuxinhua.momeyapp.utils.ToastUtils
import kotlinx.android.synthetic.main.log_fragment.*

/**
 * Created by yuxh3
 * On 2018/4/16.
 * Email by 791285437@163.com
 */
class LogFragment :BaseFragment(){

    companion object {
        fun getInstance(): Fragment {
            val loginFragment = LogFragment()
            return loginFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.log_fragment,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log_btn_login.isClickable = false
        text_input_password.transformationMethod = PasswordTransformationMethod.getInstance()
        txt_cb.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {

                if (p1){
                    text_input_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }else{
                    text_input_password.transformationMethod = PasswordTransformationMethod.getInstance()
                }

                text_input_password.setSelection(text_input_password.text.length)
            }

        })

        text_input_password.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                selectedBg()
            }

        })

        text_input_phone.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                selectedBg()
            }

        })

        onClick()
    }

    fun selectedBg(){

            if (text_input_phone.text.length>0  && text_input_password.text.length>0) {
                log_btn_login.isClickable = true
                log_btn_login.background = context.resources.getDrawable(R.drawable.log_fragment_yellow_bg)
            } else {
                log_btn_login.isClickable = false
                log_btn_login.background = context.resources.getDrawable(R.drawable.log_fragment_bg)

            }
    }

    fun onClick(){
        log_btn_login.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if (text_input_phone.text.length<=0 || text_input_phone.text.length>=12){
                    ToastUtils.setShortMsg(context,"请输入正确的手机号码")
                    return
                }
                if (text_input_password.text.length<=0 || text_input_password.text.length>=7){
                    ToastUtils.setShortMsg(context,"请输入正确的密码")
                    return
                }

                if (text_input_phone.text.toString().equals("18888888888") && text_input_password.text.toString().equals("000000")){

                    addAnimation(true)

//                    mHandler?.postDelayed(object :Runnable{
//                        override fun run() {
//
//
//                        }
//
//                    },2000)

                    mHandler?.sendEmptyMessageDelayed(0,2000)
                }else{
                    ToastUtils.setShortMsg(context,"请输入正确的账号密码")
                    return
                }
            }

        })
        log_delete.setOnClickListener {
            text_input_phone.text = null
        }

        tv_password.setOnClickListener {
            ToastUtils.setShortMsg(context,"您再想想？")
        }
    }

    override fun handlerMessage(msg: Message?) {
        super.handlerMessage(msg)

        addAnimation(false)

        (context as LoginActivity).setFinish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler?.removeMessages(0)
    }

}