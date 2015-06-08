package com.cauly.nativead;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;

public class AdListActivity extends Fragment  {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) 
	 {
		 	View view = inflater.inflate(R.layout.activity_adlist, container,false);
		 	WebView web = (WebView) view.findViewById(R.id.web);
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
		     web.setScrollBarStyle(ScrollView.SCROLLBARS_OUTSIDE_OVERLAY);
		     web.loadUrl("http://m.cauly.co.kr/nativeapps/index.html");
			return view;
	 }
	
	
	class WebClient extends WebViewClient {
     @Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}

		public boolean shouldOverrideUrlLoading(WebView view, String url) {
     	if(url.startsWith("http"))
     		view.loadUrl(url);
     	
         return true;
		} 
	 }
}
