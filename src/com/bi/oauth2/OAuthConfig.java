package com.bi.oauth2;

import java.net.URLEncoder;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import com.bi.oauth2.task.GetAccessTokenTask;


public class OAuthConfig {

	protected static final String TAG = OAuthConfig.class.getSimpleName();

	private String appKey;
	private String appSecret;
	private String OauthServerUrl;
	
	private String access_token;

	private static OAuthConfig instance;

	public static String PREFS_NAME = "BIToken";

	private String redirectUrl;

	public static OAuthConfig getInstance() {
		if (instance != null)
			return instance;
		instance = new OAuthConfig(); 
		return instance;
	}

	public void init(String... args) {
		//check input TODO
		OauthServerUrl = args[0];
		appKey = args[1];
		appSecret = args[2];
		redirectUrl = args[3];
	}

	@SuppressWarnings("deprecation")
	public String encodedRedirectUrl() {
		return URLEncoder.encode(redirectUrl);
	}

	public void getAccessCode(Activity login, Uri uri) {
		final String code = uri.getQueryParameter("code");
		Log.d(TAG, "code: " + code);
		new GetAccessTokenTask(login).execute(code);
	}
	
	
	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
	public String getOauthServerUrl() {
		return OauthServerUrl;
	}

	public void setOauthServerUrl(String oauthServerUrl) {
		OauthServerUrl = oauthServerUrl;
	}

	public String getOauthUrl() {
		return getOauthServerUrl() + "/oauth/token";
	}

	public String getAuthorizeUrl() {
		return getOauthServerUrl()+ "/oauth/authorize";
	}

	public String getAccessToken() {
		// TODO Auto-generated method stub
		return access_token;
	}

	public void setAccessToken(String s) {
		this.access_token = s;
		
	}


}
