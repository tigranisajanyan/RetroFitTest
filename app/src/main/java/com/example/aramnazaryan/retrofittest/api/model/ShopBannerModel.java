package com.example.aramnazaryan.retrofittest.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by aramnazaryan on 1/29/16.
 */
public class ShopBannerModel {

	@SerializedName("response")
	public ArrayList<ShopBanner> response;

	public static class ShopBanner {
		@SerializedName("id")
		public String id;

		@SerializedName("type")
		public String type;

		@SerializedName("tags")
		public String[] tags;

		@SerializedName("is_new")
		public boolean isNew = false;

	}

}
