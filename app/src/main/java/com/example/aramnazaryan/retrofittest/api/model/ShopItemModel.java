package com.example.aramnazaryan.retrofittest.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by aramnazaryan on 1/6/16.
 */
public class ShopItemModel {

	@SerializedName("status")
	public String status;

	@SerializedName("response")
	public ArrayList<ShopItem> itemsList = new ArrayList<>();

	public class ShopItem {
		@SerializedName("id")
		public String id;

		@SerializedName("type")
		public String type;

		@SerializedName("tags")
		public String[] tags;

		@SerializedName("is_new")
		public boolean isNew;

		@SerializedName("data")
		public ShopItemData data;

		@SerializedName("created")
		public String created;
	}

	public class ShopItemData {
		@SerializedName("price")
		public int price;

		@SerializedName("rule")
		public String rule;

		@SerializedName("banners_count")
		public int bannersCount;

		@SerializedName("preview_count")
		public int previewCount;

		@SerializedName("preview_type")
		public String previewType;

		@SerializedName("preview_size")
		public String previewSize;

		@SerializedName("color")
		public String color;

		@SerializedName("props_json")
		public String propsJson;

		@SerializedName("base_url")
		public String baseUrl;

		@SerializedName("name")
		public String name;

		@SerializedName("name_prefix")
		public String namePrefix;

		@SerializedName("shop_item_uid")
		public String shopItemUID;

		@SerializedName("description")
		public String description;

		@SerializedName("mini_description")
		public String miniDescription;
	}
}
