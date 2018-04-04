package com.example.yuxinhua.momeyapp.widgt;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuxinhua.momeyapp.R;


/**
 * Created by yuxh3
 * On 2018/3/29.
 * Email by 791285437@163.com
 */

@SuppressLint("NewApi")
public class FingerprintUiHelper extends FingerprintManager.AuthenticationCallback {
    private static final long ERROR_TIMEOUT_MILLIS = 1600;
    private static final long SUCCESS_DELAY_MILLIS = 1300;

    private final FingerprintManager mFingerprintManager;
    private final ImageView mIcon;
    private final TextView mErrorTextView;
    private final Callback mCallback;
    private CancellationSignal mCancellationSignal;
    private Context mContext;

    private boolean mSelfCancelled;

    public FingerprintUiHelper(FingerprintManager mFingerprintManager, ImageView mIcon, TextView mErrorTextView, Callback mCallback, Context context) {
        this.mFingerprintManager = mFingerprintManager;
        this.mIcon = mIcon;
        this.mErrorTextView = mErrorTextView;
        this.mCallback = mCallback;
        this.mContext = context;
    }

    public boolean isFingerprintAuthAvailable() {
        // The line below prevents the false positive inspection from Android Studio
        // noinspection ResourceType
        KeyguardManager keyguardManager =(KeyguardManager)mContext.getSystemService(Context.KEYGUARD_SERVICE);
        return mFingerprintManager.isHardwareDetected()
                && mFingerprintManager.hasEnrolledFingerprints() && keyguardManager.isDeviceSecure();
    }

    public void startListening(FingerprintManager.CryptoObject cryptoObject) {
        if (!isFingerprintAuthAvailable()) {
            return;
        }
        mCancellationSignal = new CancellationSignal();
        mSelfCancelled = false;
        // The line below prevents the false positive inspection from Android Studio
        // noinspection ResourceType
        mFingerprintManager
                .authenticate(cryptoObject, mCancellationSignal, 0 /* flags */, this, null);
        mIcon.setImageResource(R.drawable.p1);
    }

    public void stopListening() {
        if (mCancellationSignal != null) {
            mSelfCancelled = true;
            mCancellationSignal.cancel();
            mCancellationSignal = null;
        }
    }
    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        if (!mSelfCancelled) {
            showError(errString);
            mIcon.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mCallback.onError();
                }
            }, ERROR_TIMEOUT_MILLIS);
        }
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        showError(helpString);
    }

    @Override
    public void onAuthenticationFailed() {
        showError("认证失败");
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        mErrorTextView.removeCallbacks(mResetErrorTextRunnable);
        mIcon.setImageResource(R.drawable.p3);
        mErrorTextView.setTextColor(
                mErrorTextView.getResources().getColor(R.color.btn_theme_red, null));
        mErrorTextView.setText("成功");
        mIcon.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCallback.onAuthenticated();
            }
        }, SUCCESS_DELAY_MILLIS);
    }

    private void showError(CharSequence error) {
        mIcon.setImageResource(R.drawable.p5);
        mErrorTextView.setText(error);
        mErrorTextView.setTextColor(
                mErrorTextView.getResources().getColor(R.color.inverst_head_yellow, null));
        mErrorTextView.removeCallbacks(mResetErrorTextRunnable);
        mErrorTextView.postDelayed(mResetErrorTextRunnable, ERROR_TIMEOUT_MILLIS);
    }

    private Runnable mResetErrorTextRunnable = new Runnable() {
        @Override
        public void run() {
            mErrorTextView.setTextColor(
                    mErrorTextView.getResources().getColor(R.color.colorPrimary, null));
            mErrorTextView.setText("11111111");
            mIcon.setImageResource(R.drawable.p7);
        }
    };

    public interface Callback {

        void onAuthenticated();

        void onError();
    }


}
