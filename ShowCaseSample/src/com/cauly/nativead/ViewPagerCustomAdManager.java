package com.cauly.nativead;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
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

public class ViewPagerCustomAdManager 
{
	static final String APP_CODE = "PfiLgnYX";  //AppCode 설정.
	static ViewPagerCustomAdManager sManager = null;
	static HashMap<Integer, RelativeLayout> mViewMap;
	static HashMap<Integer,CaulyCustomAd> mAdMap;
	Context mContext;
	public static ViewPagerCustomAdManager getInstance()
	{
		if(sManager==null){
			sManager = new ViewPagerCustomAdManager();
			mAdMap = new HashMap<Integer, CaulyCustomAd>();
			mViewMap = new HashMap<Integer, RelativeLayout>();
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

//		@Override
//		public float getPageWidth(int position) {
//			// TODO Auto-generated method stub
//			return super.getPageWidth(position);
//		}


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
	
	
	public void requestAdView(final Context context,int ad_count, final int positionInListView)
	{
		mContext = context;
		mViewMap.put(positionInListView, new RelativeLayout(context));
		CaulyAdInfo adInfo = new CaulyNativeAdInfoBuilder(APP_CODE)
		.layoutID(R.layout.activity_native_cardlistview)
		.mainImageID(R.id.image)
		.titleID(R.id.title)
		.subtitleID(R.id.subtitle)
		.iconImageID(R.id.icon)
		.textID(R.id.description)
		.mainImageOrientation(Orientation.LANDSCAPE)
		.sponsorPosition(R.id.sponsor, Direction.RIGHT)
		.build();

		final CaulyCustomAd adview = new CaulyCustomAd(context);
		adview.setAdInfo(adInfo);
		adview.setCustomAdListener(new  CaulyCustomAdListener() {
			public void onShowedAd() {
			}
			
			//광고 호출이 실패할 경우, 호출된다.
			public void onFailedAd(int errCode, String errMsg) {
				mAdMap.remove((Integer)positionInListView);
			}
			//광고가 클릭된 경우, 호출된다.
			public void onClikedAd() {
			}
		
		
			public void onLoadedAd(boolean isChargeableAd) {
				ArrayList<CaulyNativeAdView> viewList = adview.getNativeAdViews();
				if(viewList!=null && viewList.size()>0)
				{
					CustomNativeAdViewAdapter mCustomPagerAdapter = new CustomNativeAdViewAdapter(context,adview,viewList);
					final ViewPager mViewPager = new ViewPager(context);
					mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
						
						public void onPageSelected(int arg0) {
							mViewPager.invalidate();
						}
						public void onPageScrolled(int arg0, float arg1, int arg2) {
						}
						public void onPageScrollStateChanged(int arg0) {
							
						}
					});
					mViewPager.setClipToPadding(false);
					mViewPager.setPageMargin(dpToPx(10));
					mViewPager.setPadding(dpToPx(26),0,dpToPx(26),0);
					mViewPager.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.FILL_PARENT,dpToPx(300)));
					mViewPager.setAdapter(mCustomPagerAdapter);
					mViewMap.get((Integer)positionInListView).addView(mViewPager);
					mAdMap.put((Integer)positionInListView, adview);
					mViewPager.setCurrentItem(0);
					adview.loadData(viewList.get(0));
				}
			}
		
		});
		
		adview.requestAdView(CaulyCustomAd.NATIVE_LANDSCAPE,ad_count);
	}
	private int dpToPx( int dp) {
		DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
		int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return px;
	}	
	public View getView(View convertView, int pos)
	{
		return mViewMap.get((Integer)pos);
	}
	public boolean isAdPostion(int pos)
	{
		return mViewMap.get((Integer)pos)!=null;
	}
	public void destroy() {
		
		Iterator<Integer> itr = mAdMap.keySet().iterator();
		while(itr.hasNext())
		{
			Integer key = itr.next();
			CaulyCustomAd ad =  mAdMap.get(key);
			if(ad!=null)
				ad.cancel();
			
		}
		mAdMap.clear();
		mViewMap.clear();
		
	}

}
