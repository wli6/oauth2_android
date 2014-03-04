package com.bi.oauth2;

import android.content.Context;
import android.content.Intent;

import com.bi.oauth2.task.GetResourceTask;

public class OAuthUtils {
	private static Context c;

	public static void login(Context context) {
		c = context;
		Intent loginIntent = new Intent(context, LoginActivity.class);

		context.startActivity(loginIntent);
	}

	public static void get(String url, Context context, OAuthListener listener) {
		GetResourceTask t = new GetResourceTask(context, listener);
		String params = null;
		t.execute(params);
	}

	public static void post(Context context) {

	}

	 
}
