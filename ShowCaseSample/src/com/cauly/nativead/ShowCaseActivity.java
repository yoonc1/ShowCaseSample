package com.cauly.nativead;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;

public class ShowCaseActivity extends Activity  {
	
	WebView web;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        web = new WebView(this);
        web.setLayoutParams(new LayoutParams(getWindowManager().getDefaultDisplay().getWidth(),getWindowManager().getDefaultDisplay().getHeight()));
        setContentView(web);
        web.setWebViewClient(new WebClient()); // 응룡프로그램에서 직접 url 처리
        WebSettings set = web.getSettings();
        set.setJavaScriptEnabled(true);
        set.setDomStorageEnabled(true);
        set.setBuiltInZoomControls(false);
        set.setSupportZoom(false);
        set.setJavaScriptCanOpenWindowsAutomatically(true);
        set.setAppCacheEnabled(false);
        set.setCacheMode(WebSettings.LOAD_NO_CACHE);
        set.setRenderPriority(RenderPriority.HIGH);
        set.setUseWideViewPort(true);
        web.addJavascriptInterface(new WebInterface(), "showcase");
        web.setInitialScale(1+getWindow().getWindowManager().getDefaultDisplay().getWidth()*100/640);
        web.setScrollBarStyle(ScrollView.SCROLLBARS_OUTSIDE_OVERLAY);
        web.loadUrl("http://showcase.cauly.net");
    }
	
	class WebClient extends WebViewClient {
        @Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}

		public boolean shouldOverrideUrlLoading(WebView view, String url) {
        	if(url.startsWith("http"))
        		view.loadUrl(url);
        	
        	if(url.startsWith("tel:"))
        	{
        		Intent i = new Intent(Intent.ACTION_CALL);
        		i.setData(Uri.parse(url));
        		startActivity(i);
        	}
        	if(url.startsWith("mailto:"))
        	{
        		Intent i = new Intent(Intent.ACTION_SENDTO);
        		i.setData(Uri.parse(url));
        		startActivity(i);
        	}
            return true;
        }
    }
	class WebInterface
	{
		public void show(final int id)
		{
			new Handler().postDelayed(new Runnable() {
				public void run() {
					Intent intent=  new Intent(ShowCaseActivity.this, ShowcaseVideoPlayActivity.class);
					intent.putExtra("id", id);
					startActivity(intent);
					overridePendingTransition(R.anim.push_up_in2, R.anim.push_up_out2);
				}
			},300);
			
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		web.destroy();
	}

}
