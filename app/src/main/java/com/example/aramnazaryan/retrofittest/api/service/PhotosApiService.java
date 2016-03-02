package com.example.aramnazaryan.retrofittest.api.service;

import com.example.aramnazaryan.retrofittest.api.model.User;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by aramnazaryan on 1/28/16.
 */
public interface PhotosApiService {

	@Multipart
	@POST("photos/add.json")
	Call<User> uploadPhoto(@Part("file\"; filename=\"\" ") RequestBody file, @Query("key") String key);



}
