package com.bi.oauthtest;

import java.net.URLEncoder;

import android.net.Uri;
import android.util.Log;

public abstract class OAuthConfig {

	protected static final String TAG = OAuthConfig.class.getSimpleName();

	public String appKey = "322eb986b4857456b7682dd5d7142dc97ce9fceb697f3f845684f0460c5460ac";
	public String appSecret = "26fb8b0b87a123f5adb30e2f8131c24e11d40f2380f8842320bd41a5715e45cb";
	public String redirectUrl = "";

	public OAuthConfig(String appKey, String appSecret, String redirectUrl) {
		this.appKey = appKey;
		this.appSecret = appSecret;
		this.redirectUrl = redirectUrl;
	}

	@SuppressWarnings("deprecation")
	public String encodedRedirectUrl() {
		return URLEncoder.encode(redirectUrl);
	}

	public abstract String getRefreshTokenUrl();


	public abstract String getCodeUrl();

	public abstract String getAccessCodeUrl(String code);


	public void getAccessCode(Uri uri, final OAuthListener l) {
		final String code = uri.getQueryParameter("code");
		Log.d(TAG, "code: " + code);

		String newUrl = getAccessCodeUrl(code);
		Log.d(TAG, "newUrl: " + newUrl);
	}

}
