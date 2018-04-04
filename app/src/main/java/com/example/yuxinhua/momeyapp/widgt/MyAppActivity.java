package com.example.yuxinhua.momeyapp.widgt;

import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuxinhua.momeyapp.R;
import com.example.yuxinhua.momeyapp.ui.BaseActivity;

/**
 * Created by yuxh3
 * On 2018/3/29.
 * Email by 791285437@163.com
 */

public class MyAppActivity extends BaseActivity {

    private TextView mResultInfo;
    private TextView mCancelBtn;
    private ImageView mImg;

    private FingerprintManagerCompat fingerprintManager = null;
    private MyAuthCallback myAuthCallback = null;
    private CancellationSignal cancellationSignal = null;

    public static final int MSG_AUTH_SUCCESS = 100;
    public static final int MSG_AUTH_FAILED = 101;
    public static final int MSG_AUTH_ERROR = 102;
    public static final int MSG_AUTH_HELP = 103;

  private  Handler handler = new Handler(){

      @Override
      public void handleMessage(Message msg) {
          super.handleMessage(msg);

          switch (msg.what) {
              case MSG_AUTH_SUCCESS:
                  setResultInfo("success");
                  mCancelBtn.setEnabled(false);
                  cancellationSignal = null;
                  break;
              case MSG_AUTH_FAILED:
                  setResultInfo("failed");
                  mCancelBtn.setEnabled(false);
                  cancellationSignal = null;
                  break;
              case MSG_AUTH_ERROR:
                  handleErrorCode(msg.arg1);
                  break;
              case MSG_AUTH_HELP:
                  handleHelpCode(msg.arg1);
                  break;
          }
      }
  };


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.myapp_activity);
        KeyguardManager keyguardManager =(KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        getMSpdUtils().setBoolean("isOpen",false);
        mResultInfo = (TextView) this.findViewById(R.id.fingerprint_status);
        mCancelBtn = (TextView) this.findViewById(R.id.cancel_button);
        mImg = (ImageView) this.findViewById(R.id.fingerprint_icon);

//        mCancelBtn.setEnabled(false);

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMSpdUtils().setString("isStart","cancel");
                finish();
            }
        });

        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mResultInfo.setText("请开始你的表演");
//                mResultInfo.setTextColor(Color.parseColor("#7A7E83"));

                try {
                    CryptoObjectHelper mCtyHelper = new CryptoObjectHelper();
                    if (cancellationSignal == null){
                        cancellationSignal = new CancellationSignal();
                    }
                    fingerprintManager.authenticate(mCtyHelper.buildCryptoObject(),0,cancellationSignal
                            ,myAuthCallback,null);
                    mCancelBtn.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();

                    Toast.makeText(MyAppActivity.this, "Fingerprint init failed! Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fingerprintManager = FingerprintManagerCompat.from(this);

        if (!fingerprintManager.isHardwareDetected()){
            Toast.makeText(MyAppActivity.this, "硬件不支持", Toast.LENGTH_SHORT).show();
            finish();
        }else if (!fingerprintManager.hasEnrolledFingerprints()){
            Toast.makeText(MyAppActivity.this, "是否有指纹输入", Toast.LENGTH_SHORT).show();
            finish();
        } else if (!keyguardManager.isDeviceSecure()){
            Toast.makeText(MyAppActivity.this, "是否有屏保", Toast.LENGTH_SHORT).show();
            finish();
        }else {
            myAuthCallback = new MyAuthCallback(handler);
        }

        this.setFinishOnTouchOutside(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cancellationSignal != null){
            cancellationSignal.cancel();
            cancellationSignal = null;
        }
        getMSpdUtils().setString("isStart","");
    }

    private void handleHelpCode(int code){

        switch (code) {
            case FingerprintManager.FINGERPRINT_ACQUIRED_GOOD:
                setResultInfo("FINGERPRINT_ACQUIRED_GOOD");
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_IMAGER_DIRTY:
                setResultInfo("FINGERPRINT_ACQUIRED_IMAGER_DIRTY");
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_INSUFFICIENT:
                setResultInfo("FINGERPRINT_ACQUIRED_INSUFFICIENT");
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_PARTIAL:
                setResultInfo("FINGERPRINT_ACQUIRED_PARTIAL");
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_TOO_FAST:
                setResultInfo("太快");
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_TOO_SLOW:
                setResultInfo("太慢");
                break;
        }
    }
    private void handleErrorCode(int code) {
        switch (code) {
            case FingerprintManager.FINGERPRINT_ERROR_CANCELED:
                setResultInfo("FINGERPRINT_ERROR_CANCELED");
                break;
            case FingerprintManager.FINGERPRINT_ERROR_HW_UNAVAILABLE:
                setResultInfo("FINGERPRINT_ERROR_HW_UNAVAILABLE");
                break;
            case FingerprintManager.FINGERPRINT_ERROR_LOCKOUT:
                setResultInfo("FINGERPRINT_ERROR_LOCKOUT");
                break;
            case FingerprintManager.FINGERPRINT_ERROR_NO_SPACE:
                setResultInfo("FINGERPRINT_ERROR_NO_SPACE");
                break;
            case FingerprintManager.FINGERPRINT_ERROR_TIMEOUT:
                setResultInfo("FINGERPRINT_ERROR_TIMEOUT");
                break;
            case FingerprintManager.FINGERPRINT_ERROR_UNABLE_TO_PROCESS:
                setResultInfo("FINGERPRINT_ERROR_UNABLE_TO_PROCESS");
                break;
        }
    }
    private void setResultInfo(String stringId) {
        mResultInfo.setText(stringId);
        if (mResultInfo != null) {
            mResultInfo.setTextColor(getResources().getColor(R.color.inverst_head_yellow));
            if (stringId == "success") {
                mResultInfo.setTextColor(getResources().getColor(R.color.btn_theme_red));
                getMSpdUtils().setString("isStart","success");
                finish();
            } else if (stringId.equals("FINGERPRINT_ERROR_LOCKOUT")){
                getMSpdUtils().setString("isStart","error");
                Toast.makeText(MyAppActivity.this, "验证失败，请稍后重试", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                getMSpdUtils().setString("isStart","falte");
            }
        }
    }

    private CallBack mCallBack;
    public void setCallBackListener(CallBack mCallBack){
        this.mCallBack = mCallBack;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    interface CallBack{
        void startSuccess(String code);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;
        return super.onKeyDown(keyCode, event);
    }
}
