package com.example.aramnazaryan.retrofittest;

import android.app.Application;

import com.example.aramnazaryan.retrofittest.api.RestClient;

/**
 * Created by aramnazaryan on 1/6/16.
 */
public class RetrofitTestApplication extends Application {

	private static RestClient restClient = null;

	@Override
	public void onCreate() {
		super.onCreate();
		restClient = new RestClient(getApplicationContext());
	}

	public static RestClient getRestClient() {
		return restClient;
	}
}
