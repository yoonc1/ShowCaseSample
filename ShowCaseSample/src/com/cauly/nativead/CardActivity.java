package com.cauly.nativead;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyNativeAdHelper;
import com.fsn.cauly.CaulyNativeAdInfoBuilder;
import com.fsn.cauly.CaulyNativeAdView;
import com.fsn.cauly.CaulyNativeAdViewListener;

public class CardActivity extends Fragment implements CaulyNativeAdViewListener  {

	String APP_CODE="vZxEr8bK";//"gatester";  // your app code which you are assigned.
	String[] TITLE = {"블루 & 그레이 톤의 북유럽 인테리어"
			,"인더스트리얼 카페 Nomad 디자인","닮고싶은 핀란드 아파트 인테리어 - 봄봄이와 하루님의 홈스타일",
			"맷돌순두부 by 잭슨카멜레온","PAS쟁이x팀버랜드 이벤트"};
	
	String[] SUBTITLE={"Added by Bucketplace in Korean","Added by allofthevintage in Korean ","Added by hellohouse in Korean"
	,"Added by jackson2014 in Korean"
	,"Added by PASZ2 in Korean"
	};
	String[] DESCRIPTION={"일반적으로 북유럽 인테리어라 하면 밝은 채광, 화이트 계열 배경, 그리고 나무로 포인트를 주는 게 기본인데요 :) 블루와 그레이 계열의 색깔과 독특한 소품을...","멋지네요 ^^  ",
			"리얼 북유럽인테리어! 핀란드의 한 아파트 인테리어 입니다. 화이트 인테리어와아기자기너무예쁜 소품들이 눈을 즐겁게해줍니다. 핀란드 인테리어 그룹 Lessismore 작..."
			,"천년고도 경주의 대표적인 한식당 ‘맷돌순두부’ 신관에 잭슨카멜레온 가구들이 세팅되었습니다. 테이블과 장식장은 공간 컨셉에 맞게 제작하였고, 의자는..."
			,"제 블로그로 가시면 팀버랜드 워커 코디 포스팅이 있습니다! 블로그 포스팅에 댓글을 남겨주시면 추첨 후 팀버랜드 관련 프로모션 상품(노트, USB, 연필꽂이 묶음 등) OR 문화상품권을 드립니다 경쟁률이 셀 것 같지 않네요!  많은 지원 바랄게요 :-{D"
			};
	Context context;
	public static Bitmap[] images;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ListAdapter();
        context = getActivity();
		helper=CaulyNativeAdHelper.getInstance();
		viewGroup = new ListView(getActivity());
		 mList = new ArrayList<Item>();
		 Bitmap[] icons= new Bitmap[5] ; 
		 for(int i=0; i<icons.length; i++)
		 {
			icons[i] = BitmapFactory.decodeResource( getResources(), R.drawable.c1 + i%icons.length);
		 }
		 if(images==null)
		 {
			 images= new Bitmap[5] ; 
			 for(int i=0; i<images.length; i++)
			 {
				images[i]= BitmapFactory.decodeResource( getResources(), R.drawable.d1 + i%images.length);
			 }
		 }
		 for(int i=0; i<80; i++)
		{
			mList.add(new Item(icons[ i%icons.length],images[ i%images.length],TITLE[i%TITLE.length],SUBTITLE[i%TITLE.length],DESCRIPTION[i%TITLE.length]));
		}
    }
	ListView listview; 
	ListAdapter mAdapter;
	ViewGroup viewGroup ;
	ArrayList<Item> mList;
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) 
	 {
		 	View view = inflater.inflate(R.layout.activity_card, container,false);
			listview = (ListView) view.findViewById(R.id.native_area);
			 listview.setAdapter(mAdapter);
			showNative();
			return view;
	 }
	 CustomNativeAdView adView; 
	public void showNative()
	{
		CaulyAdInfo adInfo = new CaulyNativeAdInfoBuilder(APP_CODE)
		.layoutID(R.layout.activity_native_cardlistview)
		.iconImageID(R.id.icon)
		.mainImageID(R.id.image)
		.titleID(R.id.title)
		.subtitleID(R.id.subtitle)
		.textID(R.id.description)
		.build();
		adView = new CustomNativeAdView(getActivity());
		adView.setAdInfo(adInfo);
		adView.setAdViewListener(this);
		adView.request();

	}
	public void showNativeCustom()
	{
		CaulyAdInfo adInfo = new CaulyNativeAdInfoBuilder(APP_CODE)
		.layoutID(R.layout.activity_native_cardlistview)
		.iconImageID(R.id.icon)
		.mainImageID(R.id.image)
		.titleID(R.id.title)
		.subtitleID(R.id.subtitle)
		.textID(R.id.description)
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
	@Override
	public void onDestroy() {
		super.onDestroy();
		helper.destroy();
	}
	class ListAdapter extends BaseAdapter 
	{
		public ListAdapter()
		{
		}
		public int getCount() {
			return mList.size()+1;
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
			if(helper.isAdPosition(viewGroup, position))
				return 1;
			else 
				return 0;
//			else
//				return 2;
		}
		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 2;
		}
		
		public int PixelFromDP(float dip) {
			if (context == null)
				return 0;
			
			return (int)(dip * context.getResources().getDisplayMetrics().density);
		}
		public View getView(final int position, View convertView, ViewGroup parent) {
			if( helper!=null && helper.isAdPosition(viewGroup,position) )
			{
				Animation ani = new AlphaAnimation(0.0f, 1.0f);
				ani.setDuration(1500);
				View view = helper.getView(viewGroup,position, convertView);
				View AdBackimage = view.findViewById(R.id.AdBackimage);
				AdBackimage.setBackgroundColor(Color.parseColor("yellow"));
				View image = view.findViewById(R.id.image);
				image.startAnimation(ani);
				return view;
			}
//			if(getItemViewType(position)==0)
//			{
//				View view=  View.inflate(getActivity(), R.layout.activity_cardlistview0, null);
//				return view;
//			}
			if(convertView==null )
			{
				View view=  View.inflate(getActivity(), R.layout.activity_cardlistview, null);
				view.setFocusable(false);
				TextView title = (TextView) view.findViewById(R.id.title);
				TextView subtitle = (TextView) view.findViewById(R.id.subtitle);
				TextView description = (TextView) view.findViewById(R.id.description);
				ImageView icon = (ImageView) view.findViewById(R.id.icon);
				ImageView image = (ImageView) view.findViewById(R.id.image);
				
				title.setText(getItem(position).title);
				subtitle.setText(getItem(position).subTitle);
				description.setText(getItem(position).description);
				icon.setImageBitmap(getItem(position).iconBitmap);
				image.setImageBitmap(getItem(position).imageBitmap);
				return view;
			}
			else
			{
				
				TextView title = (TextView) convertView.findViewById(R.id.title);
				TextView subtitle = (TextView) convertView.findViewById(R.id.subtitle);
				TextView description = (TextView) convertView.findViewById(R.id.description);
				ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
				ImageView image = (ImageView) convertView.findViewById(R.id.image);
				title.setText(getItem(position).title);
				subtitle.setText(getItem(position).subTitle);
				description.setText(getItem(position).description);
				icon.setImageBitmap(getItem(position).iconBitmap);
				image.setImageBitmap(getItem(position).imageBitmap);
			}
			return convertView;
		}
	}
	CaulyNativeAdHelper helper =null;
	int r=0;
	public void onFailedToReceiveNativeAd(CaulyNativeAdView adView,
			int errorCode, String errorMsg) {
		
	}

	public void onReceiveNativeAd(CaulyNativeAdView adView	, boolean isChargeableAd) {
		// TODO Auto-generated method stub
		if(r>0)
			mList.remove(r);
		r = r+4;
//		helper.removeAll(viewGroup);
		mList.add(r,null);
		helper.add(getActivity(),viewGroup,r,adView);
		mAdapter.notifyDataSetChanged();
	}
	public void onClickNativeAd() {
		// TODO Auto-generated method stub
		
	}
}
