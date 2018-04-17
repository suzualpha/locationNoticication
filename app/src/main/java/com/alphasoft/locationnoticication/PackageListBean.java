package com.alphasoft.locationnoticication;

import android.graphics.drawable.Drawable;

public class PackageListBean {
	
	private String pacageName;
	private String activityName;
	private String appName;
	private int iconResources;
	private Drawable icon;
	
	public String getPacageName() {
		return pacageName;
	}
	
	public void setPacageName(String pacageName) {
		this.pacageName = pacageName;
	}
	
	public Drawable getIcon() {
		return icon;
	}
	
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}


	public void setIconResources(int resources){
		this.iconResources = resources;
	}

	public int getIconResources(){
		return this.iconResources;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
}
