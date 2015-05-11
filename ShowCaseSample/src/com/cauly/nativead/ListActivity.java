package com.cauly.nativead;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyNativeAdHelper;
import com.fsn.cauly.CaulyNativeAdInfoBuilder;
import com.fsn.cauly.CaulyNativeAdView;
import com.fsn.cauly.CaulyNativeAdViewListener;

public class ListActivity extends Fragment implements CaulyNativeAdViewListener  {

	String APP_CODE="vZxEr8bK";//"gatester";  // your app code which you are assigned.
	CaulyNativeAdHelper helper =null;
	ArrayList<Item> mList ;
	String[] TITLE = {"빈폴 2014 S/S시즌오프 UP TO 30%+10%...","화제의 텀블러 리버스 보틀/전용파우치","제이에스티나 외 쥬얼리& 시계 여름아이템 ~ 50% OFF",
			"MACMOC 2014 Molling & Cushy Series","애플 힙, 힙업을위한다면!","꼭 한번 읽어봐야 할 책!","네스카페 돌체구스토 피콜로&캡슐"};
	
	String[] SUBTITLE={"신세계몰 해피바이러스"
	,"요즘 핫한 텀블러! 리버스보틀",
	"제이에스티나/스톤헨지/마크제이콥스/TISSOT 쥬얼리&시계 특가상품 + 추가쿠폰...",
	"신세계몰 해피바이러스 여성샌들/슬리퍼 ",
	"휴먼팩토리 애플힙",
	"책/일반도서/일반책/눈물편지/3분 통찰력/지혜의 한 줄/당신에게없는 한가지...",
	"캡슐커피 1위 네스카페! 돌체구스토 커피머신"
	};
	String[] DESCRIPTION={"0","19,500","79,800","80,100","169,000","3,500","69,000"
			};
	String[] TAG={"신세계몰 해피 바이러스"
			,"CJ오클락 ",
			"CJ오클락 ",
			"신세계몰 ",
			"티몬",
			"위메프",
			"위메프"
			};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ListAdapter();
		helper=CaulyNativeAdHelper.getInstance();
		viewGroup = new ListView(getActivity());
		 mList = new ArrayList<Item>();
		 for(int i=0; i<80; i++)
		{
			mList.add(new Item(R.drawable.a1 + i%7,TITLE[i%7],SUBTITLE[i%7],DESCRIPTION[i%7],TAG[i%7]));
		}
    }
	ListView listview; 
	ListAdapter mAdapter;
	 ViewGroup viewGroup;
	
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) 
	 {
		 	View view = inflater.inflate(R.layout.activity_list, container,false);
			listview = (ListView) view.findViewById(R.id.native_area);
			listview.setAdapter(mAdapter);
			showNative();
			return view;
	 }
	public void showNative()
	{
		CaulyAdInfo adInfo = new CaulyNativeAdInfoBuilder(APP_CODE)
		.layoutID(R.layout.activity_native_iconlist)
		.iconImageID(R.id.icon)
		.titleID(R.id.title)
		.subtitleID(R.id.subtitle)
		.build();
		CaulyNativeAdView interstial = new CaulyNativeAdView(getActivity());
		interstial.setAdInfo(adInfo);
		interstial.setAdViewListener(this);
		interstial.request();

	}
	public void showNativeCustom()
	{
		CaulyAdInfo adInfo = new CaulyNativeAdInfoBuilder(APP_CODE)
		.layoutID(R.layout.activity_native_iconlist)
		.iconImageID(R.id.icon)
		.titleID(R.id.title)
		.subtitleID(R.id.subtitle)
		.landingLayoutID(R.layout.activity_native_detail)
		.landingMainImageID(R.id.native_ad_main_image)
		.landingIconImageID(R.id.native_ad_icon_image)
		.landingTextID(R.id.native_ad_text)
		.landingTitleID(R.id.native_ad_title)
		.landingSubtitleID(R.id.native_ad_subtitle)
		.landingCloseID(R.id.native_close_btn)
		.build();
		CaulyNativeAdView interstial = new CaulyNativeAdView(getActivity());
		interstial.setAdInfo(adInfo);
		interstial.setAdViewListener(this);
		interstial.request();
	}
	
	class ListAdapter extends BaseAdapter 
	{
		public ListAdapter()
		{
		}
		public int getCount() {
			return mList.size();
		}

		public Item getItem(int position) {
			return mList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			// TODO Auto-generated method stub
			if(helper.isAdPosition(viewGroup,position))
				return 1;
			else 
				return 0;
		}
		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 2;
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			if( helper!=null && helper.isAdPosition(viewGroup, position) )
			{
				return helper.getView(viewGroup,position, convertView);
			}
			if(convertView==null)
			{
				View view=  View.inflate(getActivity(), R.layout.activity_listview, null);
				TextView title = (TextView) view.findViewById(R.id.title);
				TextView subtitle = (TextView) view.findViewById(R.id.subtitle);
				TextView description = (TextView) view.findViewById(R.id.description);
				TextView tag = (TextView) view.findViewById(R.id.tag);
				ImageView icon = (ImageView)view.findViewById(R.id.icon);
				icon.setBackgroundResource(getItem(position).img);
				title.setText(""+getItem(position).title);
				subtitle.setText(""+getItem(position).subTitle);
				tag.setText(""+getItem(position).tag);
				description.setText(""+getItem(position).description);
				return view;
			}
			else
			{
				
				TextView title = (TextView) convertView.findViewById(R.id.title);
				TextView subtitle = (TextView) convertView.findViewById(R.id.subtitle);
				ImageView icon = (ImageView)convertView.findViewById(R.id.icon);
				TextView description = (TextView) convertView.findViewById(R.id.description);
				TextView tag = (TextView) convertView.findViewById(R.id.tag);
				title.setText(""+getItem(position).title);
				subtitle.setText(""+getItem(position).subTitle);
				icon.setBackgroundResource(getItem(position).img);
				tag.setText(""+getItem(position).tag);
				description.setText(""+getItem(position).description);
			}
			return convertView;
		}
	}
	
	
	int r=8;
	public void onFailedToReceiveNativeAd(CaulyNativeAdView adView,	int errorCode, String errorMsg) {
		
	}

	public void onReceiveNativeAd(CaulyNativeAdView adView, boolean isChargeableAd) {
		// TODO Auto-generated method stub
	
		mList.add(r,null);
		helper.add(getActivity(),viewGroup,r,adView);
		r = r+4;
		mAdapter.notifyDataSetChanged();
	}
	public void onClickNativeAd() {
		// TODO Auto-generated method stub
		
	}
}
