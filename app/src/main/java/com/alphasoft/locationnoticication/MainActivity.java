package com.alphasoft.locationnoticication;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {



    final String Tag = "LocatinNotification";

    final static String CASE_APP_NAME = "case_app_name";
    final static String CASE_STRING = "case_string";
    final static String CASE_SSID = "case_ssid";

    LocationControl locationControl;

    RadioButton ssidRadiobtn;
    RadioButton locationRadiobtn;
    RadioGroup  caseRadioGroup;
    EditText    appnameEdit;
    EditText    caseStringEdit;
    SeekBar     notificationVolumeSeek;


    ImageView image;
    ImageView serectAppIcon;
    Bitmap iconImage;
    String serectPackageName = "";
    String activityName = "";

    private static final int REQUEST_CODE_KITKAT = 100;		// 画像intentの受け取り4.4以上
    private static final int REQUEST_CODE = 200;			// 画像intentの受け取り4.3以下
    private static final int REQUEST_CODE_LISTACTIVITY = 300;


    public void onSerectApp(View v) {
        Intent intent = new Intent(this, PackageListActivity.class);
        startActivityForResult(intent, REQUEST_CODE_LISTACTIVITY);
    }

    public void onSave(View v){

        String ssid = DeviceUtil.getConnectedSSID(this);
        String caseAppStr = appnameEdit.getText().toString();
        String inputCaseStr = caseStringEdit.getText().toString();

        PreferenceUtil.setValue(this, CASE_SSID,        ssid);
        PreferenceUtil.setValue(this, CASE_APP_NAME,    caseAppStr);
        PreferenceUtil.setValue(this, CASE_STRING,      inputCaseStr);

        Toast.makeText(this, "保存しました\n" + "ssid: "+ ssid+"\ncaseAppStr: " + caseAppStr + "\ninputCaseStr:" + inputCaseStr, Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_setting);

        String ssid = DeviceUtil.getConnectedSSID(this);

        ssidRadiobtn            = findViewById(R.id.wifiradiobtn);
        locationRadiobtn        = findViewById(R.id.locationradiobtn);
        caseRadioGroup          = findViewById(R.id.caseradiogroup);
        appnameEdit             = findViewById(R.id.appnameedit);
        caseStringEdit          = findViewById(R.id.casestringedit);
        notificationVolumeSeek  = findViewById(R.id.ringtonevolumeseek);

        serectAppIcon           = findViewById(R.id.packagelist_row_iconimage);

        ssidRadiobtn.setText("WIFI SSID:" + ssid);


//        appnameEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onSerectApp(v);
//            }
//        });

/*
        final TextView textView = (TextView) findViewById(R.id.kihontextview);
        final TextView textView1 = (TextView) findViewById(R.id.text1);
        final TextView textView2 = (TextView) findViewById(R.id.text2);
        final TextView textView3 = (TextView) findViewById(R.id.text3);
        final TextView textView4 = (TextView) findViewById(R.id.text4);

        Button btn1 = (Button) findViewById(R.id.locationgetbtn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadcLocation();
            }
        });

        locationControl = new LocationControl(this);
        locationControl.setLocationListener(new LocationControl.LocationControlListener() {
            @Override
            public void locationChangedListener(Location location) {
                textView1.setText(String.valueOf( location.getLatitude()) + "," + String.valueOf(location.getLongitude()));
            }

            @Override
            public void locationStatusChanged(String provider, int status, Bundle extras) {
                textView2.setText(provider + "\n" + "status:" + String.valueOf(status) + "\n extras:" + extras.toString());
            }

            @Override
            public void locationProviderEnabled(String provider) {
                textView3.setText(provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                textView4.setText(provider);
            }

        });

        Location nowlocation = locationControl.getJustLocation(this);
        //        Location location = locationManager.getLastKnownLocation(locationProvider);

        if (nowlocation != null) {

            textView.setText(String.valueOf( nowlocation.getLatitude()) + "," + String.valueOf(nowlocation.getLongitude()));
        } else {
            textView.setText("ろけーしょんなっし");
        }

        */

            if (!isEnabledReadNotification()) {
                showNotificationAccessSettingMenu();
            }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 重要：requestLocationUpdatesしたままアプリを終了すると挙動がおかしくなる。
//        locationControl.locationManager.removeUpdates(locationControl);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_LISTACTIVITY) {
                BaseApprication baseApprication = (BaseApprication) this.getApplication();
                serectAppIcon.setImageDrawable(baseApprication.getIcon());
//                serectAppIcon.setImageResource(data.getIntExtra("key_icon", 0));
                Bundle bandle = data.getExtras();
                Log.d("extra", "extra:"+ bandle.toString());


                serectPackageName = data.getStringExtra("key_package_name");
                Log.d("ぱっけーじ", serectPackageName);
                activityName = data.getStringExtra("key_activity_name");
                Log.d("アクティビティ", activityName);
                // 起動アプリ名を表示
                appnameEdit.setText(data.getStringExtra("key_app_name").toString());

//                urlEdit.setText("");
            }
            if (requestCode == REQUEST_CODE_KITKAT) {
                final int takeFlags = data.getFlags()
                        & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                // Check for the freshest data.
                Uri uri = data.getData();
                getContentResolver().takePersistableUriPermission(uri, takeFlags);
                try {
                    Bitmap bmp = Media.getBitmap(getContentResolver(), uri);
                    if (bmp != null) {
                        Bitmap dst = Bitmap.createScaledBitmap(bmp, 128, 128, true);
                        iconImage = dst;
                        if (iconImage != null) {
                            image.setImageBitmap(iconImage);
                        }
                        Log.d("pathとれた", uri.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CODE) {
                try {
                    String[] projection = { MediaStore.MediaColumns.DATA };
                    String selection = null;
                    String[] selectionArgs = null;
                    String sortOrder = null;
                    Cursor cursor = this.getContentResolver().query(data.getData(), projection, selection, selectionArgs, sortOrder);
                    if (cursor.getCount() == 1) {
                        cursor.moveToNext();
                        String filePath = cursor.getString(0);
                        if (filePath != null && 0 < filePath.length()) {
                            Log.d("ファイルパス", filePath);
                        } else {
                            Log.d("ファイルパス", "NULLです");
                            Toast.makeText(this, "ファイルを取得できませんでした", Toast.LENGTH_SHORT).show();
                        }
                        iconImage = pathToIconBitmap(filePath);
                        if (iconImage != null) {
                            image.setImageBitmap(iconImage);
                        }
//						Toast.makeText(this, filePath, Toast.LENGTH_LONG).show();
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "画像取得エラー\nネットワークドライブ上のファイルの可能性があります", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void onSerectImage(View v) {
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_KITKAT);
        }
    }


    private Bitmap pathToIconBitmap(String imageFilePath) {
        File file = new File(imageFilePath);
        if (file.exists()) {
            // デコード時のオプション
            BitmapFactory.Options options = new BitmapFactory.Options();

            // サイズだけ読み取るようにする
            options.inJustDecodeBounds = true;

            // サイズ情報を取得
            Bitmap bmp = BitmapFactory.decodeFile(imageFilePath, options);

            // 画像の高さを取得
            int height = options.outHeight;

            // 高さが128におさまるように縮小比率を設定
            options.inSampleSize = height / 128;

            // 画像の中身もデコードできるようにする
            options.inJustDecodeBounds = false;

            // 画像をデコード
            bmp = BitmapFactory.decodeFile(imageFilePath, options);

            return bmp;

        } else {
            //存在しない
            Toast.makeText(this, "ファイル存在なし", Toast.LENGTH_SHORT).show();
            return null;
        }

    }



    private void getWifiInfo(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] networks = connManager.getAllNetworks();

        if(networks == null || networks.length == 0)
            return;

        for( int i = 0; i < networks.length; i++) {
            Network ntk = networks[i];
            NetworkInfo ntkInfo = connManager.getNetworkInfo(ntk);
            if (ntkInfo.getType() == ConnectivityManager.TYPE_WIFI && ntkInfo.isConnected() ) {
                final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (connectionInfo != null) {
                    // add some code here
                }
            }

        }
    }

    private void showNotificationAccessSettingMenu() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
        startActivity(intent);
    }

    private boolean isEnabledReadNotification() {
        ContentResolver contentResolver = getContentResolver();
        String rawListeners = Settings.Secure.getString(contentResolver,
                "enabled_notification_listeners");
        if (rawListeners == null || "".equals(rawListeners)) {
            return false;
        } else {
            String[] listeners = rawListeners.split(":");
            for (String listener : listeners) {
                if (listener.startsWith(getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }




    public void reloadcLocation(){
        final TextView textView = (TextView) findViewById(R.id.kihontextview);
        Location nowlocation = locationControl.getJustLocation(this);
        if (nowlocation != null) {
            textView.setText(String.valueOf( nowlocation.getLatitude()) + "," + String.valueOf(nowlocation.getLongitude()));
        } else {
            textView.setText("ろけーしょんなっし");
        }
    }
}
