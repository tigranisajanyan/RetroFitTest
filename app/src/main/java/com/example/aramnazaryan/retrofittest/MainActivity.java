package com.example.aramnazaryan.retrofittest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aramnazaryan.retrofittest.api.model.ShopBannerModel;
import com.example.aramnazaryan.retrofittest.api.model.ShopItemModel;
import com.example.aramnazaryan.retrofittest.api.model.User;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

	private TextView statusCodeTextView = null;
	private TextView millisecondsTextView = null;
	private long startTime = 0;
	private int requestCount = 0;
	private static final int REQUESTS_COUNT = 1000;
	private static final String TAG_SHOP_MULTIPLE_REQUEST = "shop_multiple_request";

	private View getShopItemsButton = null;
	private View getShopItemsMultipleButton = null;
	private View loginUserButton = null;
	private View getShopBannersButton = null;
	private View uploadPhotoButton = null;
	private ProgressDialog progress =  null;
	private ProgressDialog horizontalProgress =  null;

	private static final int REQUEST_IMAGE = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		statusCodeTextView = (TextView) findViewById(R.id.txt_status_code);
		millisecondsTextView = (TextView) findViewById(R.id.txt_time_in_millis);

		getShopItemsButton = findViewById(R.id.getShopItems);
		getShopItemsMultipleButton = findViewById(R.id.getShopItemsMultiple);
		loginUserButton = findViewById(R.id.loginUser);
		uploadPhotoButton = findViewById(R.id.uploadPhoto);
		getShopBannersButton = findViewById(R.id.getShopBanners);

		progress = new ProgressDialog(MainActivity.this);
		progress.setMessage(getString(R.string.gen_lease_wait));
		horizontalProgress = new ProgressDialog(MainActivity.this);
		horizontalProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		horizontalProgress.setMax(REQUESTS_COUNT);


		getShopItemsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startTime = System.currentTimeMillis();
				progress.show();
				RetrofitTestApplication.getRestClient().getShopApiService().getShopItems().enqueue(new Callback<ShopItemModel>() {
					@Override
					public void onResponse(Response<ShopItemModel> response) {
						progress.dismiss();
						logSingleResponse(String.valueOf(response.code()));
						Log.d("TestHttp", "OkHttp-Selected-Protocol: " + response.headers().get("OkHttp-Selected-Protocol"));
						Log.d("TestHttp", "Response code is " + response.code());
					}

					@Override
					public void onFailure(Throwable t) {
						progress.dismiss();
						logSingleResponse("Error");
					}
				});
			}
		});

		getShopItemsMultipleButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startTime = System.currentTimeMillis();
				requestCount = 0;
				statusCodeTextView.setText("Pending");
				horizontalProgress.show();
				for (int i = 0; i < REQUESTS_COUNT; i++) {
					RetrofitTestApplication.getRestClient().getShopApiService().getShopItems().enqueue(new Callback<ShopItemModel>() {
						@Override
						public void onResponse(Response<ShopItemModel> response) {
							logMultipleResponse(String.valueOf(response.code()));
						}

						@Override
						public void onFailure(Throwable t) {
							logMultipleResponse("Error");
							horizontalProgress.dismiss();
						}
					});
				}
			}
		});

		loginUserButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startTime = System.currentTimeMillis();
				progress.show();
				RetrofitTestApplication.getRestClient().getUserApiService().userLogin("android", "tttttt", "TTTttt").enqueue(new Callback<User>() {
					@Override
					public void onResponse(Response<User> response) {
						getPreferences(MODE_PRIVATE).edit().putString("key", response.body().key).commit();
						initView();
						progress.dismiss();
					}

					@Override
					public void onFailure(Throwable t) {
						logSingleResponse("Error");
						progress.dismiss();
					}
				});
			}
		});

		uploadPhotoButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setType("image/*");
				startActivityForResult(intent, REQUEST_IMAGE);
			}
		});


		getShopBannersButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				progress.show();
				startTime = System.currentTimeMillis();
				HashMap<String, String> params = new HashMap<>();
				params.put("app", "com.picsart.studio");
				params.put("market", "google");
				params.put("type", "com.picsart.studio");
				RetrofitTestApplication.getRestClient().getShopApiService().getShopBanners(params).enqueue(new Callback<ShopBannerModel>() {
					@Override
					public void onResponse(Response<ShopBannerModel> response) {
						progress.dismiss();
						logSingleResponse(response.code() == 200 ? "success " +response.raw().protocol() : "failed "+response.raw().protocol());
					}

					@Override
					public void onFailure(Throwable t) {
						progress.dismiss();
						logSingleResponse("failed");
					}
				});
			}
		});


		initView();

	}


	private void logSingleResponse(String status) {
		long time = System.currentTimeMillis() - startTime;
		statusCodeTextView.setText(status);
		millisecondsTextView.setText(String.valueOf(time));
	}

	private void logMultipleResponse(String status) {
		requestCount++;
		Log.d(TAG_SHOP_MULTIPLE_REQUEST, "received " + requestCount + " with status " + status);
		if (requestCount == REQUESTS_COUNT) {
			long time = System.currentTimeMillis() - startTime;
			statusCodeTextView.setText("Finished");
			millisecondsTextView.setText(String.valueOf(time));
			horizontalProgress.dismiss();
		} else {
			horizontalProgress.setProgress(requestCount);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_IMAGE) {
				String path = getPath(data);
				progress.show();
				File file = new File(path);
				RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

				RetrofitTestApplication.getRestClient().getPhotosApiService().uploadPhoto(fileBody, getPreferences(MODE_PRIVATE).getString("key", "")).enqueue(new Callback<User>() {
					@Override
					public void onResponse(Response<User> response) {
						Toast.makeText(getApplicationContext(), R.string.gen_success, Toast.LENGTH_SHORT).show();
						progress.dismiss();
					}

					@Override
					public void onFailure(Throwable t) {
						Toast.makeText(getApplicationContext(), R.string.gen_failed, Toast.LENGTH_SHORT).show();
						progress.dismiss();
					}
				});
			}
		}
	}

	private void initView() {
		if (!TextUtils.isEmpty(getPreferences(MODE_PRIVATE).getString("key", ""))) {
			getShopItemsButton.setVisibility(View.VISIBLE);
			getShopItemsMultipleButton.setVisibility(View.VISIBLE);
		} else {
			getShopItemsButton.setVisibility(View.GONE);
			getShopItemsMultipleButton.setVisibility(View.GONE);
		}
	}

	public String getPath(Intent data) {
		String path = null;
		try {
			Uri uri = data.getData();
			if (uri != null) {
				String[] projection = {MediaStore.Images.Media.DATA};
				Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
				if (cursor != null) {
					int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					path = cursor.getString(columnIndex);
				} else {
					path = uri.getPath();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		//TODO try to change ACTION_GET_CONTENT to ACTION_PICK and test on all devices.
		if (TextUtils.isEmpty(path)) {
			// failed to get path
			try {
				String uriString = data.getDataString().replaceAll("%3A", "/");
				Uri uri = Uri.parse(uriString);
				if (uri != null) {
					String[] projection = {MediaStore.Images.Media.DATA};
					Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
					if (cursor != null) {
						int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						cursor.moveToFirst();
						path = cursor.getString(columnIndex);
					} else {
						path = uri.getPath();
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return path;
	}
}
