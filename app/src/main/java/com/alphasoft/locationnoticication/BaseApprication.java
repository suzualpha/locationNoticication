package com.alphasoft.locationnoticication;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class BaseApprication extends Application {
	 private final String TAG = "DEBUG-APPLICATION";
	    private Drawable icon;
	 
	    @Override
	    public void onCreate() {
	        /** Called when the Application-class is first created. */
	        Log.v(TAG,"--- onCreate() in ---");
	    }
	 
	    @Override
	    public void onTerminate() {
	        /** This Method Called when this Application finished. */
	        Log.v(TAG,"--- onTerminate() in ---");
	    }
	 
	    public void setIcon(Drawable bmp){
	    	icon = bmp;
	    }
	 
	    public Drawable getIcon(){
	    	return icon;
	    }
}
