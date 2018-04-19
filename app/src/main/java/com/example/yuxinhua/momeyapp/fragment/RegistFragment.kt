package com.example.yuxinhua.momeyapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.example.yuxinhua.momeyapp.R
import kotlinx.android.synthetic.main.regist_fragment.*

/**
 * Created by yuxh3
 * On 2018/4/16.
 * Email by 791285437@163.com
 */
class RegistFragment:BaseFragment(){

    companion object {
        fun getInstance(): Fragment {
            val registFragment = RegistFragment()
            return registFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.regist_fragment,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text_regist_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                selectedBg()
            }

        })
        text_regist_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                selectedBg()
            }

        })
        text_regist_picture.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                selectedBg()
            }

        })

        cb_regist.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                    selectedBg()
            }

        })
    }

    fun selectedBg(){

        if (text_regist_phone.text.length>0  && text_regist_password.text.length>0
                && text_regist_picture.text.length>0 && cb_regist.isChecked) {
            regist_btn_next.background = context.resources.getDrawable(R.drawable.log_fragment_yellow_bg)
        } else {
            regist_btn_next.background = context.resources.getDrawable(R.drawable.log_fragment_bg)
        }
    }
}