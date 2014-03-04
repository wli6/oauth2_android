package com.bi.oauth2;

import java.net.URLEncoder;

import com.bi.oauthtest.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginActivity extends Activity {

	private OAuthConfig mConfig;
	private WebView mWebView;
	private OAuthConfig config;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		config = OAuthConfig.getInstance();
		mWebView = (WebView) findViewById(R.id.webView1);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new OAuthWebViewClient());

		String mUrl = config.getAuthorizeUrl() + "?client_id="
				+ config.getAppKey() + "&response_type=code&redirect_uri="
				+ URLEncoder.encode(config.getRedirectUrl());
		mWebView.loadUrl(mUrl);
		mConfig = new OAuthConfig();
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
			if (url.startsWith(config.getRedirectUrl())) {  
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
					mConfig.getAccessCode(LoginActivity.this,uri);

				}
			}
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}
	}

}
