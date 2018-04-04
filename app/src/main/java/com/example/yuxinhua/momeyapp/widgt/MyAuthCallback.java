package com.example.yuxinhua.momeyapp.widgt;

import android.os.Handler;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

import com.example.yuxinhua.momeyapp.ui.MainActivity;


/**
 * Created by yuxh3
 * On 2018/3/29.
 * Email by 791285437@163.com
 */

public class MyAuthCallback extends FingerprintManagerCompat.AuthenticationCallback {

    private Handler handler = null;

    public MyAuthCallback(Handler handler) {
        super();

        this.handler = handler;
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        super.onAuthenticationError(errMsgId, errString);

        if (handler != null) {
            handler.obtainMessage(MyAppActivity.MSG_AUTH_ERROR, errMsgId, 0).sendToTarget();
        }
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        super.onAuthenticationHelp(helpMsgId, helpString);

        if (handler != null) {
            handler.obtainMessage(MyAppActivity.MSG_AUTH_HELP, helpMsgId, 0).sendToTarget();
        }
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);

        if (handler != null) {
            handler.obtainMessage(MyAppActivity.MSG_AUTH_SUCCESS).sendToTarget();
        }
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();

        if (handler != null) {
            handler.obtainMessage(MyAppActivity.MSG_AUTH_FAILED).sendToTarget();
        }
    }
}
