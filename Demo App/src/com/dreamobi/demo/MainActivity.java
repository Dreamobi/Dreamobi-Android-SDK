package com.dreamobi.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.dreamobi.dac.Dreamobi;
import com.dreamobi.dac.ad.o.DreamobiAd;
import com.dreamobi.dac.ad.o.DreamobiAdListener;
import com.dreamobi.dac.ad.o.DreamobiInterstitialWall;
import com.dreamobi.dac.ad.o.DreamobiVideoAd;

public class MainActivity extends Activity implements DreamobiAdListener, OnClickListener {
	
	final private Handler handler = new Handler();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Dreamobi.configure(MainActivity.this, "token", "package.name", "金币");
		Button videoButton = (Button) findViewById(R.id.videoButton);
		videoButton.setOnClickListener(this);
		Button videoWallButton = (Button) findViewById(R.id.videoWallButton);
		videoWallButton.setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Dreamobi.resume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Dreamobi.pause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Dreamobi.release();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.videoButton:
			DreamobiVideoAd video = new DreamobiVideoAd("video_ad_id").withListener(this);
			video.show();
			break;
		case R.id.videoWallButton:
			DreamobiInterstitialWall wall = new DreamobiInterstitialWall().withListener(this);
			wall.show();
			break;
		}
		
	}
	
	private void showToast(final Context ctx, final String text) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onDreamobiAdAttemptFinished(DreamobiAd ad) {
		if (ad.noFill()) {
			showToast(MainActivity.this, "No Ad available now!");
		} else if (ad.noNetwork()) {
			showToast(MainActivity.this, "No network!");
		}
	}

	@Override
	public void onDreamobiAdStarted(final DreamobiAd ad) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(MainActivity.this, "获得" + ad.getPoints() + "金币", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
}
