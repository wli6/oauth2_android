package com.bi.oauthtest;

 

public interface OAuthListener {
	void onSuccess(Token token);

	void onCancel();

	void onError(String error);
}