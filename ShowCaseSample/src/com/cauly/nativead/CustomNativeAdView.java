package com.cauly.nativead;

import android.content.Context;
import android.widget.Toast;

import com.fsn.cauly.CaulyNativeAdView;

public class CustomNativeAdView extends CaulyNativeAdView  {

	Context context;
	public CustomNativeAdView(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public void onClickAd() {
		super.onClickAd();
		Toast.makeText(context, "onclick",Toast.LENGTH_SHORT).show();
	}


}
