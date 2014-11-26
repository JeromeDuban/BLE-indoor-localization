package com.eirb.projets9;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class SplashScreen extends Activity {
	
	private Timer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.splashscreen);

		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.hyperspace_jump);
		this.findViewById(R.id.img).startAnimation(hyperspaceJumpAnimation);

		timer = new Timer();
		timer.schedule(new TimerTask(){
			public void run(){
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		}, 3000);
	}

	public boolean onTouch(View v, MotionEvent event) {
		if(this.timer != null){
			timer.cancel();
			Intent i = new Intent(SplashScreen.this, MainActivity.class);
			startActivity(i);
			finish();
		}
		return false;
	}
}