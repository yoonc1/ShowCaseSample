package com.cauly.nativead;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Browser;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CaulyInfoActivity extends Fragment  {
	ViewPager listview ;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) 
	 {
		 	View view = inflater.inflate(R.layout.activity_caulyinfo, container,false);
		 	final View advertiserView = view.findViewById(R.id.layout_advertiser);
			final View developerView = view.findViewById(R.id.layout_development);
		 	final View left = view.findViewById(R.id.left_btn);
		 	final View right = view.findViewById(R.id.right_btn);
		 	final TextView text_left = (TextView) view.findViewById(R.id.text1);
		 	final TextView text_right = (TextView) view.findViewById(R.id.text2);
		 	ImageView go_cauly = (ImageView) view.findViewById(R.id.go_cauly);
		 	go_cauly.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.cauly.net"));
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.addCategory(Intent.CATEGORY_BROWSABLE);
					intent.putExtra(Browser.EXTRA_APPLICATION_ID, getActivity().getApplicationContext().getPackageName());
					getActivity().getApplicationContext().startActivity(intent);
				}
			});
		 	left.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					advertiserView.setVisibility(View.VISIBLE);
					developerView.setVisibility(View.GONE);
					left.setSelected(true);
			 		text_left.setTextColor(getActivity().getResources().getColor(R.color.white));
					right.setSelected(false);
					text_right.setTextColor(getActivity().getResources().getColor(R.color.orange));
				}
			});
		 	right.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					advertiserView.setVisibility(View.GONE);
					developerView.setVisibility(View.VISIBLE);
					left.setSelected(false);
			 		text_left.setTextColor(getActivity().getResources().getColor(R.color.orange));
					right.setSelected(true);
					text_right.setTextColor(getActivity().getResources().getColor(R.color.white));
				}
			});
		 	
	 		advertiserView.setVisibility(View.VISIBLE);
			developerView.setVisibility(View.GONE);
	 		left.setSelected(true);
	 		text_left.setTextColor(getActivity().getResources().getColor(R.color.white));
			right.setSelected(false);
			text_right.setTextColor(getActivity().getResources().getColor(R.color.orange));
		
			listview = (ViewPager) view.findViewById(R.id.layout_development);
		 	 MyAdapter adapter = new MyAdapter(getActivity());
		    listview.setAdapter(adapter);
		    listview.setCurrentItem(0);
			return view;
	 }
	 
	 private class MyAdapter extends PagerAdapter{
	    	private LayoutInflater mInflater;
	    	
	    	public MyAdapter( Context con) {
				super();
				mInflater = LayoutInflater.from(con.getApplicationContext());
			}
	    	  public int getCount() { return 1; }	
	    	  public Object instantiateItem(View pager, int position) {
	    		  View v;
    			   v = 	 mInflater.inflate(R.layout.activity_caulyinfo_developer1, null);
    			 	ImageView go_cauly = (ImageView) v.findViewById(R.id.go_cauly);
    			 	go_cauly.setOnClickListener(new OnClickListener() {
    					public void onClick(View v) {
    						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.cauly.net"));
    						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    						intent.addCategory(Intent.CATEGORY_BROWSABLE);
    						intent.putExtra(Browser.EXTRA_APPLICATION_ID, getActivity().getApplicationContext().getPackageName());
    						getActivity().getApplicationContext().startActivity(intent);
    					}
    				});
	    		  listview.addView(v);
	    		return v; 
	    	}
	    	
			public void destroyItem(View pager, int position, Object view) {
				//((ViewPager)pager).removeView((View)view);
			}
			  public boolean isViewFromObject(View view, Object obj) { return view == obj; }
			  public void finishUpdate(View arg0) {}
			  public void restoreState(Parcelable arg0, ClassLoader arg1) {}
			  public Parcelable saveState() { return null; }
			  public void startUpdate(View arg0) {}
	    }	
	
}
