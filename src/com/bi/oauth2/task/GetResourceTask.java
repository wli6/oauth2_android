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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.bi.oauth2.OAuthConfig;
import com.bi.oauth2.OAuthListener;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

public class GetResourceTask extends AsyncTask<String, String, String> {

		private Context context;
		private OAuthListener listener;
		private String url;

		public GetResourceTask(Context context, OAuthListener l) {
			this.url = url;
			this.context = context;
			this.listener = l;
		}

		@Override
		protected String doInBackground(String... params) {
			 OAuthConfig config = OAuthConfig.getInstance();
			String result = null;
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response;

				HttpGet httpget = new HttpGet(config.getOauthServerUrl() + url);			
				
				httpget.setHeader(
						"Authorization",
						"Bearer "
								+config.getAccessToken());
				httpget.setHeader("Content-Type", "application/json");

				response = httpclient.execute(httpget);

				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					listener.onSuccess(out.toString());
					out.close();

				} else if (statusLine.getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
					listener.onError(statusLine.getReasonPhrase());
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