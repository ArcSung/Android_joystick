package com.arc.joystick_android;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Window;

public class JoystickActivity extends Activity {

	private int mSurfaceYDisplayRange;
	private float mTouchY1, mTouchY2, mTouchX1, mTouchX2;
	public final static String TAG = "Arc/Android_Joystick";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_joystick);
	}
	
	 @Override
	    public boolean onTouchEvent(MotionEvent event) {


	        DisplayMetrics screen = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(screen);
	        int pointTouched = event.getPointerCount(); // get touch point count
	        
	        float y_changed1, y_changed2 = 0;
	        float x_changed1, x_changed2 = 0;
	        float coef1,coef2;
	        float xgesturesize1, xgesturesize2; 

	        if (mSurfaceYDisplayRange == 0)
	            mSurfaceYDisplayRange = Math.min(screen.widthPixels, screen.heightPixels);

	        y_changed1 = (float)(event.getY(0) - mTouchY1);
	        x_changed1 = (float)(event.getX(0) - mTouchX1);
	        // coef is the gradient's move to determine a neutral zone
        	coef1 = Math.abs (y_changed1 / x_changed1);
        	xgesturesize1 = ((x_changed1 / screen.xdpi) * 2.54f);
	        
	        if(pointTouched == 2){
		        y_changed2 = (float)(event.getY(1) - mTouchY2);
		        x_changed2 = (float)(event.getX(1) - mTouchX2);
		        // coef is the gradient's move to determine a neutral zone
	        	coef2 = Math.abs (y_changed2 / x_changed2);
	        	xgesturesize2 = ((x_changed2 / screen.xdpi) * 2.54f);
	        }
	      
	        
	        switch (event.getAction()) {

	        case MotionEvent.ACTION_DOWN:
	        	mTouchY1 = event.getY(0);  //init mTouch
	        	mTouchX1 = event.getX(0);
	        	if(pointTouched == 2){
		        	mTouchY2 = event.getY(1);  //init mTouch
		        	mTouchX2 = event.getX(1);
	        	}
	        break;

	        	case MotionEvent.ACTION_MOVE:
	        		// No audio/brightness action if coef < 2
	        		if (coef1 > 2) {
	        			// Audio (Up or Down - Right side)
	                if (mTouchX1 > (screen.widthPixels / 2)){
	                    	Log.i(TAG, "y right:"+y_changed1);
	                	}	
	                // Brightness (Up or Down - Left side)
	                if (mTouchX1 < (screen.widthPixels / 2)){
	                		Log.i(TAG, "x left:"+x_changed1);
	                	}
	        		}
	        		if(pointTouched == 2){
	        			if (coef1 > 2) {
		        			// Audio (Up or Down - Right side)
		                if (mTouchX2 > (screen.widthPixels / 2)){
		                    	Log.i(TAG, "y right2:"+y_changed2);
		                	}	
		                // Brightness (Up or Down - Left side)
		                if (mTouchX2 < (screen.widthPixels / 2)){
		                		Log.i(TAG, "x left2:"+x_changed2);
		                	}
	        			}
	        		}
	        		break;

	        	case MotionEvent.ACTION_UP:
	        		break;
	        	}
	        
	        return true;
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.joystick, menu);
		return true;
	}

}
