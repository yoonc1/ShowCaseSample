package com.cauly.nativead;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyCloseAd;
import com.fsn.cauly.CaulyCloseAdListener;
/**
 * This demonstrates how you can implement switching between the tabs of a
 * TabHost through fragments, using FragmentTabHost.
 */
public class FragmentActivityTest extends FragmentActivity implements CaulyCloseAdListener{
    private FragmentTabHost mTabHost;
	private static final String APP_CODE = "mBZgr3Ch"; // 광고 요청을 위한 App Code
	CaulyCloseAd mCloseAd ;;                            // CloseAd광고 객체
	LinearLayout titleLayout;
	Typeface mTypeface;
	int lastTab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_activity);
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        titleLayout = (LinearLayout) findViewById(R.id.title);
        LayoutParams param = titleLayout.getLayoutParams();
        param.height = getWindowManager().getDefaultDisplay().getWidth()*124/640;
        mTabHost.addTab(mTabHost.newTabSpec("simple").setIndicator(create(R.drawable.tab01)), CardActivity.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("contacts").setIndicator(create(R.drawable.tab02)),ImageActivity.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("custom").setIndicator(create(R.drawable.tab03)), ListActivity.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("multi").setIndicator(create(R.drawable.tab04)), NativeMultiAdDataActivity.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("info").setIndicator(create(R.drawable.tab05)), AdListActivity.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("adlist").setIndicator(create(R.drawable.tab06)), CaulyInfoActivity.class, null);
        mTabHost.getTabWidget().getChildAt(4).setVisibility(View.GONE);
        mTabHost.getTabWidget().getChildAt(5).setVisibility(View.GONE);
        View info = findViewById(R.id.info);
        info.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if( mTabHost.getCurrentTab()!=5)
					lastTab = mTabHost.getCurrentTab();
				mTabHost.setCurrentTab(5);
			}
		});
        
        View inven = findViewById(R.id.inven);
        inven.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if( mTabHost.getCurrentTab()!=4)
					lastTab = mTabHost.getCurrentTab();
				mTabHost.setCurrentTab(4);
			}
		});
        
        mTabHost.setCurrentTab(0);
        //CloseAd 초기화 
		CaulyAdInfo closeAdInfo = new CaulyAdInfoBuilder(APP_CODE).build();
		mCloseAd = new CaulyCloseAd();
		
		/*  Optional
			//원하는 버튼의 문구를 설젇 할 수 있다.  
			mCloseAd.setButtonText("취소", "종료");
			//원하는 텍스트의 문구를 설젇 할 수 있다.  
			mCloseAd.setDescriptionText("종료하시겠습니까?");
		*/
		mCloseAd.setAdInfo(closeAdInfo);
		mCloseAd.setCloseAdListener(this); // CaulyCloseAdListener 등록
		
		 if (mTypeface == null)
	            mTypeface = Typeface.createFromAsset(getAssets(), "font.ttf.mp3");
//	        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
//	        setGlobalFont(root);
		
    }
    ImageView create(int id)
    {
    	ImageView view = new ImageView(this);
    	view.setImageDrawable(getResources().getDrawable(id));
    	DisplayMetrics dm = new DisplayMetrics();
    	getWindowManager().getDefaultDisplay().getMetrics(dm);
    	view.setPadding((int)(5*dm.density), (int)(5*dm.density), (int)(5*dm.density), (int)(5*dm.density));
    	return view;
    } 
    
    @Override
	protected void onResume() {
		mCloseAd.resume(this); // 필수 호출 
		super.onResume();
	}
	

	public  void setGlobalFont(ViewGroup root) {
	    for (int i = 0; i < root.getChildCount(); i++) {
	        View child = root.getChildAt(i);
	        if (child instanceof TextView){
	            ((TextView)child).setTypeface(mTypeface);
	        }
	        else if (child instanceof ViewGroup)
	            setGlobalFont((ViewGroup)child);
	    }
	}
	
	

	// Back Key가 눌러졌을 때, CloseAd 호출
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			if(mTabHost.getCurrentTab()==3)
				mTabHost.setCurrentTab(lastTab);
			else 
				finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showDefaultClosePopup()
	{
		new AlertDialog.Builder(this).setTitle("").setMessage("종료 하시겠습니까?")
		   .setPositiveButton("예", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {
		     finish();
		    }
		   })
		   .setNegativeButton("아니요",null)
		   .show();
	}

    // CaulyCloseAdListener
	
	public void onFailedToReceiveCloseAd(CaulyCloseAd arg0, int arg1,String arg2) {
	}
	
	// CloseAd의 광고를 클릭하여 앱을 벗어났을 경우 호출되는 함수이다. 
	public void onLeaveCloseAd(CaulyCloseAd arg0) {
	}

	// CloseAd의 request()를 호출했을 때, 광고의 여부를 알려주는 함수이다.  
	public void onReceiveCloseAd(CaulyCloseAd arg0, boolean arg1) {
	}
	
	//왼쪽 버튼을 클릭 하였을 때, 원하는 작업을 수행하면 된다. 
	public void onLeftClicked(CaulyCloseAd arg0) {
		
	}
	
	//오른쪽 버튼을 클릭 하였을 때, 원하는 작업을 수행하면 된다. 
	//Default로는 오른쪽 버튼이 종료로 설정되어있다. 
	public void onRightClicked(CaulyCloseAd arg0) {
		finish();
	}

	public void onShowedCloseAd(CaulyCloseAd arg0, boolean arg1) {
		
	}
}