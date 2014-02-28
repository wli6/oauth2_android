package com.bi.oauthtest;

public class BIConfig extends OAuthConfig {

	String scope;

	public BIConfig(String appKey, String appSecret, String redirectUrl) {
		super(appKey, appSecret, redirectUrl);
	}

	public String getCodeUrl() {
		return "http://10.100.131.116:3000/service/auth2/auth?client_id="
				+ appKey + "&grant_type=client_credentials";
	}

	@Override
	public String getAccessCodeUrl(String code) {
		return "http://10.100.131.116:3000/oauth/authorize?client_id=" + appKey
				+ "&client_secret=" + appSecret
				+ "&grant_type=authorization_code";
	}

	public String getAccessTokenParams(String code) {

		return null;
	}

	public String getRefreshTokenUrl() {
		return "http://10.100.131.116:3000/service/auth2/token";
	}

	public String getRefreshTokenParams(String refreshToken) {

		return null;
	}

	 
}
