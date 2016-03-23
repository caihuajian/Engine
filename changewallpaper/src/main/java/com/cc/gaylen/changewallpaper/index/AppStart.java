package com.cc.gaylen.changewallpaper.index;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.cc.gaylen.changewallpaper.R;

public class AppStart extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.appstart);
			
		new Handler().postDelayed(new Runnable(){
		@Override
		public void run(){
			Intent intent = new Intent (AppStart.this,MainActivity.class);			
			startActivity(intent);			
			AppStart.this.finish();
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
		}
	}, 2500);
   }
}