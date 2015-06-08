package com.cauly.nativead;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfo.Direction;
import com.fsn.cauly.CaulyAdInfo.Orientation;
import com.fsn.cauly.CaulyNativeAdInfoBuilder;
import com.fsn.cauly.CaulyNativeAdView;
import com.fsn.cauly.CaulyNativeAdViewListener;

public class ImageActivity extends Fragment implements CaulyNativeAdViewListener  {

	String APP_CODE="mBZgr3Ch";//"gatester";  // your app code which you are assigned.
	RelativeLayout ad1, ad2;
	CaulyNativeAdView adView1, adView2;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) 
	 {
		 	View view = inflater.inflate(R.layout.activity_image, container,false);
		 	ad1 = (RelativeLayout) view.findViewById(R.id.ad1);
		 	ad2 = (RelativeLayout) view.findViewById(R.id.ad2);
			showNative();
			showNative2();
			return view;
	 }
	 
	public void showNative()
	{
		CaulyAdInfo adInfo = new CaulyNativeAdInfoBuilder(APP_CODE)
		.layoutID(R.layout.activity_native_image1)
		.mainImageID(R.id.image)
		.mainImageOrientation(Orientation.LANDSCAPE)
		.sponsorPosition(R.id.sponsor, Direction.CENTER)
		.build();
		if(adView1!=null)
			adView1.destroy();
		 adView1 = new CaulyNativeAdView(getActivity());
		adView1.setAdInfo(adInfo);
		adView1.setTag("1");
		adView1.setAdViewListener(this);
		adView1.request();
		ad1.addView(adView1);

	}
	
	public void showNative2()
	{
		CaulyAdInfo adInfo = new CaulyNativeAdInfoBuilder(APP_CODE)
		.layoutID(R.layout.activity_native_image2)
		.mainImageID(R.id.image)
		.titleID(R.id.title)
		.mainImageOrientation(Orientation.LANDSCAPE)
		.sponsorPosition(R.id.sponsor, Direction.CENTER)
		.adRatio("300x300")
		.build();
		if(adView2!=null)
			adView2.destroy();
		adView2 = new CaulyNativeAdView(getActivity());
		adView2.setAdInfo(adInfo);
		adView2.setTag("2");
		adView2.setAdViewListener(this);
		adView2.request();
		ad2.addView(adView2);
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if(adView1!=null)
			adView1.destroy();
		if(adView2!=null)
			adView2.destroy();
	}

	@Override
	public void onDestroy() {
		if(adView1!=null)
			adView1.destroy();
		if(adView2!=null)
			adView2.destroy();
		super.onDestroy();
	}
	public void onFailedToReceiveNativeAd(CaulyNativeAdView adView,int errorCode, String errorMsg) {
		
	}

	public void onReceiveNativeAd(CaulyNativeAdView adView, boolean isChargeableAd) {
//		if("1".equals(adView.getTag()))
//		{
//			adView.attachToView(ad1);
//		}
//		else if("2".equals(adView.getTag()))
//		{
//			adView.attachToView(ad2);
//		}
	}


	public void onClickNativeAd() {
		// TODO Auto-generated method stub
		
	}

}
