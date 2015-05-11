package com.cauly.nativead;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfo.Direction;
import com.fsn.cauly.CaulyAdInfo.Orientation;
import com.fsn.cauly.CaulyCustomAd;
import com.fsn.cauly.CaulyCustomAdListener;
import com.fsn.cauly.CaulyNativeAdInfoBuilder;
import com.fsn.cauly.CaulyNativeAdView;

public class ViewPagerCustomAdManager2 
{
	static final String APP_CODE = "PfiLgnYX";  //AppCode 설정.
	static ViewPagerCustomAdManager2 sManager = null;
	int positionInListView;
	CaulyCustomAd adview ;
	Context mContext;
	ViewPager mViewPager;
	RelativeLayout layout;
	public static ViewPagerCustomAdManager2 getInstance()
	{
		if(sManager==null){
			sManager = new ViewPagerCustomAdManager2();
		}
		return sManager;
	}
	
	class CustomNativeAdViewAdapter extends PagerAdapter {
		Context mContext;
		CaulyCustomAd customAd;
		LayoutInflater mLayoutInflater;
		ArrayList<CaulyNativeAdView> viewlist;
		public CustomNativeAdViewAdapter(Context context, CaulyCustomAd adview, ArrayList<CaulyNativeAdView> list) {
			mContext = context;
			viewlist = list;
			customAd = adview;
			mLayoutInflater = (LayoutInflater) mContext	.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}


		@Override
		public int getCount() {
			return viewlist.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view ==  object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			CaulyNativeAdView adView = viewlist.get(position);
			customAd.loadData(adView);
			container.addView(adView);
			return adView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position,	Object object) {
			container.removeView((View) object);
		}
	}	

	public void requestAdView(final Context context, int ad_count, final int positionInListView)
	{
		mContext = context;
		layout = new RelativeLayout(context);
		this.positionInListView = positionInListView;
		CaulyAdInfo adInfo = new CaulyNativeAdInfoBuilder(APP_CODE)
		.layoutID(R.layout.activity_native_multilistview2)
		.mainImageID(R.id.image)
		.mainImageOrientation(Orientation.LANDSCAPE)
		.sponsorPosition(R.id.sponsor, Direction.CENTER)
		.build();
		if(adview!=null)
			adview.cancel();
		adview = new CaulyCustomAd(context);
		adview.setAdInfo(adInfo);
		adview.setCustomAdListener(new  CaulyCustomAdListener() {
			public void onShowedAd() {
			}
			
			//광고 호출이 실패할 경우, 호출된다.
			public void onFailedAd(int errCode, String errMsg) {
			}
			//광고가 클릭된 경우, 호출된다.
			public void onClikedAd() {
			}
		
		
			public void onLoadedAd(boolean isChargeableAd) {
				final ArrayList<CaulyNativeAdView> viewList = adview.getNativeAdViews();
				if(viewList!=null && viewList.size()>0)
				{
					CustomNativeAdViewAdapter mCustomPagerAdapter = new CustomNativeAdViewAdapter(context,adview,viewList);
					final View view = View.inflate(context, R.layout.activity_native_multilistview3, null);
//					Log.i("Changju","vo   "+view);
					mViewPager =(ViewPager) view.findViewById(R.id.pager);
					mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
						public void onPageSelected(int pos) {
							setSelectButton(view, pos);
						}
						
						public void onPageScrolled(int arg0, float arg1, int arg2) {
						}
						public void onPageScrollStateChanged(int arg0) {
							
						}
					});
					mViewPager.setAdapter(mCustomPagerAdapter);
					mViewPager.setCurrentItem(0);
					adview.loadData(viewList.get(0));
					layout.addView(view);
					
				}
			}
		
		});
		adview.requestAdView(CaulyCustomAd.NATIVE_LANDSCAPE,ad_count);
	}
	
	private void setSelectButton(View view, int i)
	{
		if(view==null)
			return;
		View btn1 = view.findViewById(R.id.b1);
		View btn2 = view.findViewById(R.id.b2);
		View btn3 = view.findViewById(R.id.b3);
		if(btn1==null)
			return;
		btn1.setBackgroundResource(R.drawable.dot);
		btn2.setBackgroundResource(R.drawable.dot);
		btn3.setBackgroundResource(R.drawable.dot);
		if(0==i)
		{
			btn1.setBackgroundResource(R.drawable.dot_selected);
		}
		else if(1==i)
		{
			btn2.setBackgroundResource(R.drawable.dot_selected);
		}
		else
		{
			btn3.setBackgroundResource(R.drawable.dot_selected);
		}
	}
	private int dpToPx( int dp) {
		DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
		int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return px;
	}	
	public void destroy() {
		
		if(adview!=null)
			adview.cancel();
		
	}
	public View getView(View convertView, int pos)
	{
		return layout;
	}
	public boolean isAdPostion(int pos)
	{
		return pos == positionInListView;
	}
}
