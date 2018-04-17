package com.alphasoft.locationnoticication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PackageListAdapter extends ArrayAdapter<PackageListBean> {
	private LayoutInflater layoutInflater_;
	
	public PackageListAdapter(Context context, int textViewResourceId, List<PackageListBean> objects) {
		super(context, textViewResourceId, objects);
		layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 特定の行(position)のデータを得る
		PackageListBean item = (PackageListBean) getItem(position);
		
		// convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
		if (null == convertView) {
			convertView = layoutInflater_.inflate(R.layout.packagelist_row, null);
		}
		
		// CustomDataのデータをViewの各Widgetにセットする
		ImageView imageView;
		imageView = (ImageView) convertView.findViewById(R.id.packagelist_row_iconimage);
		imageView.setImageDrawable(item.getIcon());
		
		TextView appName;
		appName = (TextView) convertView.findViewById(R.id.packagelist_row_name);
		appName.setText(item.getAppName());
		
		TextView packageName;
		packageName = (TextView) convertView.findViewById(R.id.packagelist_row_packagename);
		packageName.setText(item.getPacageName());
		
		
		return convertView;
	}
}