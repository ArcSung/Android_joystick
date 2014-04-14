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
	private float mTouchY, mTouchX;
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

	        if (mSurfaceYDisplayRange == 0)
	            mSurfaceYDisplayRange = Math.min(screen.widthPixels, screen.heightPixels);

	        float y_changed = event.getRawY() - mTouchY;
	        float x_changed = event.getRawX() - mTouchX;

	        // coef is the gradient's move to determine a neutral zone
	        float coef = Math.abs (y_changed / x_changed);
	        float xgesturesize = ((x_changed / screen.xdpi) * 2.54f);

	        switch (event.getAction()) {

	        case MotionEvent.ACTION_DOWN:
	            mTouchY = event.getRawY();  //init mTouch
	            mTouchX = event.getRawX();
	            break;

	        case MotionEvent.ACTION_MOVE:
	            // No audio/brightness action if coef < 2
	            if (coef > 2) {
	                // Audio (Up or Down - Right side)
	                if (mTouchX > (screen.widthPixels / 2)){
	                    Log.i(TAG, "y right:"+y_changed);
	                }
	                // Brightness (Up or Down - Left side)
	                if (mTouchX < (screen.widthPixels / 2)){
	                	Log.i(TAG, "x left:"+x_changed);
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
