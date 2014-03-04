package com.bi.oauth2;

public interface OAuthListener {
	void onSuccess(String token);

	void onCancel();

	void onError(String error);
}