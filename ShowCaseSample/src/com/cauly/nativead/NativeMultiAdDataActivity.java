package com.cauly.nativead;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfo.Direction;
import com.fsn.cauly.CaulyAdInfo.Orientation;
import com.fsn.cauly.CaulyNativeAdHelper;
import com.fsn.cauly.CaulyNativeAdInfoBuilder;
import com.fsn.cauly.CaulyNativeAdView;
import com.fsn.cauly.CaulyNativeAdViewListener;

public class NativeMultiAdDataActivity extends Fragment  {

	String APP_CODE="vZxEr8bK";// your app code which you are assigned.
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
	ListView listview; 
	ListAdapter mAdapter;
	ArrayList<Item> mList;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ListAdapter();
        context = getActivity();
		 mList = new ArrayList<Item>();
		 Bitmap[] icons= new Bitmap[5] ; 
		 for(int i=0; i<80; i++)
		{
			mList.add(new Item( R.drawable.a1 +i%icons.length,R.drawable.d1 +i%icons.length,TITLE[i%TITLE.length],SUBTITLE[i%TITLE.length],DESCRIPTION[i%TITLE.length],""));
		}
		 
    }

	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) 
	 {
		
		 	View view = inflater.inflate(R.layout.activity_multiad, container,false);
			listview = (ListView) view.findViewById(R.id.native_area);
			listview.setAdapter(mAdapter);
			
			ViewPagerCustomAdManager2.getInstance().requestAdView(getActivity(), 3,0);
			
			ViewPagerCustomAdManager.getInstance().requestAdView(getActivity(), 4, 2);
			ViewPagerCustomAdManager.getInstance().requestAdView(getActivity(), 4, 7);
			
			return view;
	 }
	 
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		ViewPagerCustomAdManager.getInstance().destroy();
		ViewPagerCustomAdManager2.getInstance().destroy();
	}
	class ListAdapter extends BaseAdapter 
	{
		private static final int YOUR_ITEM_TYPE = 0;
		private static final int YOUR_ITEM_COUNT = 1;
		
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

		// 새로운 네이티브애드 타입이 존재하기 때문에 하나를 등록해준다. 
		@Override
		public int getItemViewType(int position) {
			if(ViewPagerCustomAdManager2.getInstance().isAdPostion(position))
				return YOUR_ITEM_TYPE+2;
			else if(ViewPagerCustomAdManager.getInstance().isAdPostion(position))
				return YOUR_ITEM_TYPE+1;
			else 
				return YOUR_ITEM_TYPE;
		}
		
		// 기존의 레이아웃타입 + 1 의 총개수 등록
		@Override
		public int getViewTypeCount() {
			return YOUR_ITEM_COUNT+2;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			
			// CaulyNativeAdHelper를 이용하여, 현재 리스트뷰와 등록한 포지션을 이용하여 , 현재 뷰가 NativeAd인지 아닌지를 반환한다.
			if(ViewPagerCustomAdManager2.getInstance().isAdPostion(position))
			{
				return ViewPagerCustomAdManager2.getInstance().getView(convertView,position);
			}
			else if(ViewPagerCustomAdManager.getInstance().isAdPostion(position))
			{
				return ViewPagerCustomAdManager.getInstance().getView(convertView,position);
			}
			else
			{
				//기존의 getView 코드 구현
				if(convertView==null)
				{
					View view=  View.inflate(context, R.layout.activity_native_multilistview, null);
					view.setFocusable(false);
					TextView title = (TextView) view.findViewById(R.id.title);
					TextView subtitle = (TextView) view.findViewById(R.id.subtitle);
					TextView description = (TextView) view.findViewById(R.id.description);
					ImageView icon = (ImageView) view.findViewById(R.id.icon);
					ImageView image = (ImageView) view.findViewById(R.id.image);
					
					title.setText(getItem(position).title);
					subtitle.setText(getItem(position).subTitle);
					description.setText(getItem(position).description);
					icon.setImageResource(getItem(position).icon);
					image.setImageResource(getItem(position).img);
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
					icon.setImageResource(getItem(position).icon);
					image.setImageResource(getItem(position).img);
				}
			}
			return convertView;
		}
	}
}
