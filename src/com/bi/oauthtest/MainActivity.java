package com.bi.oauthtest;

import java.net.URLEncoder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
	public String appKey = "322eb986b4857456b7682dd5d7142dc97ce9fceb697f3f845684f0460c5460ac";
	public String appSecret = "26fb8b0b87a123f5adb30e2f8131c24e11d40f2380f8842320bd41a5715e45cb";
	public String redirectUrl = "http://10.100.131.116:3000";
	private OAuthConfig mConfig;
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
   
		mWebView = (WebView) findViewById(R.id.webView1);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new OAuthWebViewClient());

		String mUrl = "http://10.100.131.116:3000/oauth/authorize?client_id="
				+ appKey + "&response_type=code&redirect_uri=" + 
				URLEncoder.encode("http://10.100.131.116:4000");
		mWebView.loadUrl(mUrl);
		mConfig = new BIConfig(appKey, appSecret, redirectUrl);
	}
   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {  
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class OAuthWebViewClient extends WebViewClient {

		private static final String TAG = "main";

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			Log.d(TAG, "url: " + url);
			if (url.startsWith(redirectUrl)) {
				view.stopLoading();
				Uri uri = Uri.parse(url);
				String error = uri.getQueryParameter("error");
				if (error != null) {
					Log.d(TAG, "error: " + error);
					if (error.equals("access_denied")) {
						// mOAuthListener.onCancel();
					} else {
						// mOAuthListener.onError(error);
					}
				} else {
					mConfig.getAccessCode(uri, new OAuthListener() {
						@Override
						public void onSuccess(Token token) {
							// OAuthDialog.this.dismiss();
							// mOAuthListener.onSuccess(token);
							Log.d(TAG, "success");
						}

						@Override
						public void onError(String error) {
						}

						@Override
						public void onCancel() {
						}
					});
				}
			}
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}
	}

}
