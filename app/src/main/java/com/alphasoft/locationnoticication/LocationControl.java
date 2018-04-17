package com.alphasoft.locationnoticication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class LocationControl implements LocationListener {


    /*
    * activity へコールバック渡す為のinterface
    * コールバックを増やす時は抽象メソッドを追加して
    * 本来コールバック受け取るクラス（LocationCotrol）で定義 と
    * コールバックを受け取りたいクラス（MainActivity）で定義 し処理を行う
    * */

    public interface LocationControlListener {
        void locationChangedListener(Location location);
        void locationStatusChanged(String provider, int status, Bundle extras);
        void locationProviderEnabled(String provider);
        void onProviderDisabled(String provider);
    }


    public LocationControl(Activity activity){

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            /** fine location のリクエストコード（値は他のパーミッションと被らなければ、なんでも良い）*/
            final int requestCode = 1;

//             いずれも得られていない場合はパーミッションのリクエストを要求する
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, requestCode );
            return;
        }

        // 位置情報を管理している LocationManager のインスタンスを生成する
        locationManager = (LocationManager) activity.getSystemService(activity.LOCATION_SERVICE);

        String locationProvider = null;

        // GPSが利用可能になっているかどうかをチェック
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        }
        // GPSプロバイダーが有効になっていない場合は基地局情報が利用可能になっているかをチェック
        else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        }
        // いずれも利用可能でない場合は、GPSを設定する画面に遷移する
        else {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activity.startActivity(settingsIntent);
            return;
        }

        // FIXME 本来であれば、リスナが複数回登録されないようにチェックする必要がある
        locationManager.requestLocationUpdates(locationProvider, minTime, minDistance, this);
    }

    /** 位置情報の通知するための最小時間間隔（ミリ秒） */
    final long minTime = 500;
    /** 位置情報を通知するための最小距離間隔（メートル）*/
    final long minDistance = 1;
    /** 別クラス（activity）で受け渡すようのListener */
    private LocationControlListener listener;

    LocationManager locationManager;



    //リスナーを追加するメソッド
    public void setLocationListener(LocationControlListener listener)
    {
        this.listener = listener;
    }
    
    private String getLocationProvider(){

        String locationProvider = "";
        // GPSが利用可能になっているかどうかをチェック
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        }
        // GPSプロバイダーが有効になっていない場合は基地局情報が利用可能になっているかをチェック
        else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        }
        Log.d("getLocationProvider", locationProvider);
        return locationProvider;
    }

    public Location getJustLocation(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        } else {
            return locationManager.getLastKnownLocation(getLocationProvider());
        }
    }


    // 以下本来の LocationListener のListener
    @Override
    public void onLocationChanged(Location location) {
        listener.locationChangedListener(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        listener.locationStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(String provider) {
        listener.locationProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        listener.onProviderDisabled(provider);
    }
}


