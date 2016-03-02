package com.example.aramnazaryan.retrofittest.api.service;

import com.example.aramnazaryan.retrofittest.api.model.ShopBannerModel;
import com.example.aramnazaryan.retrofittest.api.model.ShopItemModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by aramnazaryan on 1/6/16.
 */
public interface ShopApiService {

	@GET("apps.json?app=com.picsart.studio&market=google&type=apps&price=paid&offset=0&limit=1")
	Call<ShopItemModel> getShopItems();

	@GET("apps.json")
	Call<ShopBannerModel> getShopBanners(@QueryMap Map<String, String> params);




}
