package com.alphasoft.locationnoticication;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ogawatsukasaEX on 2018/03/22.
 */

public class NotificationService extends NotificationListenerService {

    private String TAG = "Notification";
    private final int dateRange = 2000;

    final String checkPackageName = "jp.naver.line.android";

    final int SOUND_POOL_MAX = 6;

    boolean hantei = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("NotificationService", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("NotificationService", "onDestroy");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d("NotificationService", "onNotificationPosted start");
        hantei = false;



        String caseAppStr = PreferenceUtil.getValue(this, MainActivity.CASE_APP_NAME);
        String inputCaseStr = PreferenceUtil.getValue(this, MainActivity.CASE_STRING);
        String ssid = PreferenceUtil.getValue(this, MainActivity.CASE_SSID);

        Log.d("NotificationService", "onNotificationPosted:CASE_APP_NAME:" + caseAppStr);
        Log.d("NotificationService", "onNotificationPosted:CASE_STRING:" + inputCaseStr);

        StatusBarNotification[] notifications = getActiveNotifications();
        for (StatusBarNotification object: notifications){
            int id = object.getId();
            String gorupKey = object.getGroupKey();
            String key = object.getKey();
//            String aa = (String) object.getNotification().getSettingsText();


            String package_name = object.getPackageName();
            String post_date = new SimpleDateFormat("yyyy/MM/dd").format(object.getPostTime());
            long postTime = object.getPostTime();
            CharSequence ticket = object.getNotification().tickerText;
            String message;

            if (ticket != null) {
                message = ticket.toString();
            } else {
                message = "なし";
            }
            Log.d("NotificationService", "====================================================================================================-" );
//            Log.d("NotificationService", "notification"+"\nID:" + String.valueOf(id) + "\npackage_name:" + package_name + "\npostTime:"+ String.valueOf(postTime) + "\nMessage:" + message + "\nGroupKey:" + gorupKey + "\nKey:" + key);

            Log.d("NotificationService", "onNotificationPosted パッケージ名判定[ checkPackageName:" + package_name + " checkPackageName:"+ checkPackageName + "]");
            if(package_name.equals(checkPackageName)){
                Toast.makeText(this,"onNotificationPosted LINE",Toast.LENGTH_SHORT).show();
                Log.d("NotificationService", "onNotificationPosted アプリ判定[ message:" + message + " inputCaseStr:"+ inputCaseStr + "]");
                if(message.contains(inputCaseStr)){
                    Log.d("NotificationService", "onNotificationPosted SSID判定[ saveSSID:" + ssid + " nowSSID:"+ DeviceUtil.getConnectedSSID(this) + "]");
                    if(ssid.equals(DeviceUtil.getConnectedSSID(this))) {
                        Log.d("NotificationService", "onNotificationPosted 時間判定[ getTime:" + String.valueOf(new Date().getTime()) + " postData:"+ String.valueOf(postTime) + " 差:" + String.valueOf((new Date().getTime() - postTime)) + "]");
                        if((new Date().getTime() - postTime) < dateRange){
                            Log.d("NotificationService", "onNotificationPosted[ 音鳴らす判定◯ ]");
                            hantei = true;
//                            sendNoticationSE();
                        } else {Log.d("NotificationService", "onNotificationPosted[時間×]"); }
                    } else {Log.d("NotificationService", "onNotificationPosted[SSID×]"); }
                } else {Log.d("NotificationService", "onNotificationPosted[反応メッ セージ×]"); }
            } else {Log.d("NotificationService", "onNotificationPosted[パッケージ名×]"); }
//            Log.d(TAG + " onNotificationPosted", package_name + ":" + post_date + ":" + message);
        }

        if(hantei){
            Toast.makeText(this,"ここでSE",Toast.LENGTH_SHORT).show();
//            sendNoticationSE();
        }

        /*
        final AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        final int originalVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.d("NotificationService", "originalVolume:" + String.valueOf(originalVolume));
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        final int maxVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.d("NotificationService", "maxVolume:" + String.valueOf(maxVolume));
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);


        MediaPlayer mp = MediaPlayer.create(this,
                Settings.System.DEFAULT_NOTIFICATION_URI);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {s
            @Override
            public void onCompletion(MediaPlayer mp)
            {

                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);
            }
        });
*/
        Log.d("NotificationService", "onNotificationPosted end");
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.d("NotificationService", "onNotificationRemoved:" + sbn.toString());
        //通知が削除
    }

    private void sendNoticationSE(){
        final AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        final int originalVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.d("NotificationService", "originalVolume:" + String.valueOf(originalVolume));
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        final int maxVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.d("NotificationService", "maxVolume:" + String.valueOf(maxVolume));
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);


        MediaPlayer mp = MediaPlayer.create(this,
                Settings.System.DEFAULT_NOTIFICATION_URI);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);
            }
        });

    }


    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private SoundPool buildSoundPool(int poolMax)
    {
        SoundPool pool = null;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            pool = new SoundPool(poolMax, AudioManager.STREAM_MUSIC, 0);
        }
        else {
            AudioAttributes attr = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            pool = new SoundPool.Builder()
                    .setAudioAttributes(attr)
                    .setMaxStreams(poolMax)
                    .build();
        }

        return pool;
    }

}