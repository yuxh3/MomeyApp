package com.example.yuxinhua.momeyapp.widgt;

import android.annotation.SuppressLint;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Log;

import java.security.Key;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/**
 * Created by yuxh3
 * On 2018/3/29.
 * Email by 791285437@163.com
 */

public class CryptoObjectHelper {

    // This can be key name you want. Should be unique for the app.
    static final String KEY_NAME = "android_yuxinhua_momey_app";

    // We always use this keystore on Android.
    static final String KEYSTORE_NAME = "AndroidKeyStore";

    // Should be no need to change these values.
    static final String KEY_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES;
    static final String BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC;
    static final String ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7;
    static final String TRANSFORMATION = KEY_ALGORITHM + "/" +
            BLOCK_MODE + "/" +
            ENCRYPTION_PADDING;
    final KeyStore _keystore;

    public CryptoObjectHelper() throws Exception
    {
        _keystore = KeyStore.getInstance(KEYSTORE_NAME);
        _keystore.load(null);
    }

    public FingerprintManagerCompat.CryptoObject buildCryptoObject() throws Exception
    {
        @SuppressLint({"NewApi", "LocalSuppress"}) Cipher cipher = createCipher(true);
//        Log.i("yuxh3","------>"+cipher.doFinal());
        return new FingerprintManagerCompat.CryptoObject(cipher);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    Cipher createCipher(boolean retry) throws Exception
    {
        Key key = GetKey();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        try
        {
            cipher.init(Cipher.ENCRYPT_MODE | Cipher.DECRYPT_MODE, key);
        } catch(KeyPermanentlyInvalidatedException e)
        {
            _keystore.deleteEntry(KEY_NAME);
            if(retry)
            {
                createCipher(false);
            } else
            {
                throw new Exception("Could not create the cipher for fingerprint authentication.", e);
            }
        }
        return cipher;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    Key GetKey() throws Exception
    {
        Key secretKey;
        if(!_keystore.isKeyEntry(KEY_NAME))
        {
            CreateKey();
        }

        secretKey = _keystore.getKey(KEY_NAME, null);
        return secretKey;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void CreateKey() throws Exception
    {
        KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM, KEYSTORE_NAME);
        KeyGenParameterSpec keyGenSpec =
                new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(BLOCK_MODE)
                        .setEncryptionPaddings(ENCRYPTION_PADDING)
                        .setUserAuthenticationRequired(true)
                        .build();
        keyGen.init(keyGenSpec);
        keyGen.generateKey();
    }
}
