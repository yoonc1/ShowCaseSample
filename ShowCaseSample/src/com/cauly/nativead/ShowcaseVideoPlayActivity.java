package com.cauly.nativead;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.AssetManager.AssetInputStream;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.cauly.nativead.ShowCaseActivity.WebInterface;
import com.google.gson.Gson;

public class ShowcaseVideoPlayActivity extends Activity  {
	
	WebView web, web2;
	VideoView video; 
	int i;
	Content content;
	Integer[] src =  { R.raw.stonehenge, R.raw.hearthstone, R.raw.sk2,R.raw.samsungmusic,R.raw.nivea, R.raw.blackyak, R.raw.anf, R.raw.hitejinro, R.raw.chevrolet};
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent()==null) 
        	return ;
        Gson mGson = new Gson();
        Contents items = mGson.fromJson(assect_jsonParser("showcase/contents.json"), Contents.class);
        i = getIntent().getIntExtra("id", 10);
        content = items.list.get(i);
        RelativeLayout layout  =  new RelativeLayout(this);
        layout.setLayoutParams(new LayoutParams(getWindowManager().getDefaultDisplay().getWidth(),getWindowManager().getDefaultDisplay().getHeight()));
        setContentView(layout);
        web = new WebView(this);
        web.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
        layout.addView(web);
        video = new VideoView(this);
        RelativeLayout.LayoutParams lp =  new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,getWindowManager().getDefaultDisplay().getWidth()*360/640 );
        lp.topMargin =getWindowManager().getDefaultDisplay().getWidth()*366/640;
        video.setLayoutParams(lp);
        Uri uri = Uri.parse("android.resource://"+  getPackageName() + "/raw/"+src[i-10]);
        video.setVideoURI(uri);
        video.setOnCompletionListener(new OnCompletionListener() {
			
			public void onCompletion(MediaPlayer mp) {
				video.start();
			}
		});
        layout.addView(video);
        
        web.setWebViewClient(new WebClient()); // 응룡프로그램에서 직접 url 처리
        WebSettings set = web.getSettings();
        set.setJavaScriptEnabled(true);
        set.setDomStorageEnabled(true);
        set.setBuiltInZoomControls(false);
        set.setSupportZoom(false);
        set.setJavaScriptCanOpenWindowsAutomatically(true);
        set.setRenderPriority(RenderPriority.HIGH);
        web.setInitialScale(1+getWindow().getWindowManager().getDefaultDisplay().getWidth()*100/640);
        web.loadUrl("file:///android_asset/showcase/"+content.linkUrl);
        
        web2 = new WebView(this);
        web2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
        set = web2.getSettings();
        set.setJavaScriptEnabled(true);
        set.setDomStorageEnabled(true);
        set.setBuiltInZoomControls(false);
        set.setSupportZoom(false);
        set.setJavaScriptCanOpenWindowsAutomatically(true);
        set.setRenderPriority(RenderPriority.HIGH);
        web2.setBackgroundColor(Color.TRANSPARENT);
        web2.setInitialScale(1+getWindow().getWindowManager().getDefaultDisplay().getWidth()*100/640);
        layout.addView(web2);   
        web2.loadUrl("file:///android_asset/showcase/hello.html" );
        web2.addJavascriptInterface(new WebInterface(), "showcase");
        web2.setWebChromeClient(new WebChromeClient() {  
            @Override  
            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result)   
            {  
                new AlertDialog.Builder(ShowcaseVideoPlayActivity.this)  
                    .setTitle("javaScript dialog")  
                    .setMessage(message)  
                    .setPositiveButton(android.R.string.ok,  
                            new AlertDialog.OnClickListener()   
                            {  
                                public void onClick(DialogInterface dialog, int which)   
                                {  
                                    result.confirm();  
                                }  
                            })  
                    .setCancelable(false)  
                    .create()  
                    .show();  
                  
                return true;  
            };  
        });  
    }
	class WebInterface
	{
		public void close()
		{
			finish();
			overridePendingTransition(R.anim.push_up_in2, R.anim.push_up_out2);
		}
		
		public void play()
		{
			video.seekTo(0);
			video.start();
		}
	}
	class WebClient extends WebViewClient {
        @Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			video.start();
			 web2.loadUrl("javascript:" + "window.setInfo('"+content.titleKo+"','"+content.titleEn+"','"+content.productName+"','"+content.techName+"','"+content.description+"')");

		}

		public boolean shouldOverrideUrlLoading(WebView view, String url) {
        	if(url.startsWith("http"))
        		view.loadUrl(url);
            return true;
        }
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			finish();
			overridePendingTransition(R.anim.push_up_in2, R.anim.push_up_out2);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		web.destroy();
	}
		public String assect_jsonParser(String filePath) {
			AssetManager mAssetManager = null;
			String jString;

			mAssetManager = getResources().getAssets();
			jString = "";

			try {
				AssetInputStream ais = (AssetInputStream) mAssetManager
						.open(filePath);

				BufferedReader br = new BufferedReader(new InputStreamReader(ais));
				StringBuilder sb = new StringBuilder();

				int bufferSize = 1024 * 1024;
				char readBuf[] = new char[bufferSize];

				int resultSize = 0;

				while ((resultSize = br.read(readBuf)) != -1) {
					if (resultSize == bufferSize) {
						sb.append(readBuf);
					} else {
						for (int i = 0; i < resultSize; i++) {
							sb.append(readBuf[i]);
						}
					}
				}
				jString += sb.toString();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			
			return jString;
		}




}
