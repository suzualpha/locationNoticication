package com.alphasoft.locationnoticication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PackageListActivity extends Activity implements OnItemClickListener{
	
	ListView packageListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_package_list);
		
		packageListView = (ListView) findViewById(R.id.pakcagelistview);
		packageListView.setOnItemClickListener(this);
		
		setupListView();
	}
	
	void setupListView(){
		ArrayList<PackageListBean> appList = new ArrayList<PackageListBean>();
		// パッケージマネージャーの作成
		PackageManager packageManager = getPackageManager();
		// ランチャーから起動出来るアプリケーションの一覧
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> appInfo = packageManager.queryIntentActivities(intent, 0);
		if (appInfo != null) {
			for (ResolveInfo info : appInfo) {
				PackageListBean itemData = new PackageListBean();
				
				itemData.setPacageName(info.activityInfo.packageName.toString());
//				Log.d("package",info.activityInfo.packageName.toString());
				itemData.setAppName(info.loadLabel(packageManager).toString());
//				Log.d("appname",info.loadLabel(packageManager).toString());
				itemData.setActivityName(info.activityInfo.name.toString());
//				Log.d("activityname",info.activityInfo.name.toString());
				itemData.setIconResources(info.getIconResource());
				itemData.setIcon(info.loadIcon(packageManager));
				appList.add(itemData);
			}
		}
		
		// リスト表示設定
		PackageListAdapter adapter = new PackageListAdapter(this, R.layout.packagelist_row, appList);
		packageListView.setAdapter(adapter);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		PackageListBean serectPackageBean = (PackageListBean) packageListView.getItemAtPosition(position);
		Log.v("TAG", position + "　行目の　" + serectPackageBean.getPacageName() + "　がクリックされた");
		BaseApprication baseApprication = (BaseApprication) this.getApplication();
		baseApprication.setIcon(serectPackageBean.getIcon());

		
		Intent data = new Intent();
		data.putExtra("key_icon", serectPackageBean.getIconResources());
		data.putExtra("key_package_name", serectPackageBean.getPacageName());
		data.putExtra("key_activity_name", serectPackageBean.getActivityName());
		data.putExtra("key_app_name", serectPackageBean.getAppName());
		setResult(RESULT_OK, data);
		finish();
		
	}
	
}
