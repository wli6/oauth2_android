package com.bi.oauth2.task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.bi.oauth2.OAuthConfig;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;

public class GetAccessTokenTask extends AsyncTask<String, String, String> {

	private Activity LoginActivity;

	public GetAccessTokenTask(Activity login) {
		this.LoginActivity = login;
	}

	@Override
	protected String doInBackground(String... params) {

		OAuthConfig config = OAuthConfig.getInstance();
		String responseString = null;
		String result = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;

			HttpPost httppost = new HttpPost(config.getOauthUrl());

			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("client_id",
					config.getAppKey()));
			postParameters.add(new BasicNameValuePair("client_secret",
					config.getAppSecret()));
			postParameters.add(new BasicNameValuePair("code", params[0]));
			postParameters.add(new BasicNameValuePair("grant_type",
					"authorization_code"));
			postParameters.add(new BasicNameValuePair("redirect_uri",
					config.getRedirectUrl()));

			httppost.setEntity(new UrlEncodedFormEntity(postParameters));

			response = httpclient.execute(httppost);

			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();

				JSONObject obj;
				try {
					obj = new JSONObject(responseString);
					result = obj.getString("access_token");

					if (result != null) {
						SharedPreferences settings = LoginActivity.getBaseContext()
								.getSharedPreferences(OAuthConfig.PREFS_NAME, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.putString("token", result);
						
						config.setAccessToken(result);
						LoginActivity.finish();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
}
