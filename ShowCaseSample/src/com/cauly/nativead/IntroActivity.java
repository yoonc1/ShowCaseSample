package com.cauly.nativead;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyInterstitialAd;
import com.fsn.cauly.CaulyInterstitialAdListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

public class IntroActivity extends Activity implements CaulyInterstitialAdListener {
	
	WebView web;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        View showcase = findViewById(R.id.showcase);
        
        
//        CaulyAdInfo adInfo = new CaulyAdInfoBuilder("sOdz2T6Z").build();
//        CaulyInterstitialAd interstial = new CaulyInterstitialAd();
//		interstial.setAdInfo(adInfo);
//		interstial.setInterstialAdListener(this);
//		interstial.requestInterstitialAd(this);
        View nativead = findViewById(R.id.nativead);
        showcase.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(IntroActivity.this, ShowCaseActivity.class));
			}
		});
        nativead.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(IntroActivity.this, FragmentActivityTest.class));
			}
		});
    }

	public void onClosedInterstitialAd(CaulyInterstitialAd arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onFailedToReceiveInterstitialAd(CaulyInterstitialAd arg0,
			int arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	public void onLeaveInterstitialAd(CaulyInterstitialAd arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onReceiveInterstitialAd(CaulyInterstitialAd arg0, boolean arg1) {
		//arg0.show();
	}
	

}
