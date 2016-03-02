package com.example.aramnazaryan.retrofittest.api.service;

import com.example.aramnazaryan.retrofittest.api.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by aramnazaryan on 1/8/16.
 */
public interface UserApiService {

	@FormUrlEncoded
	@POST("users/signin.json")
	Call<User> userLogin(@Field("provider") String provider, @Field("username") String username, @Field("password") String password);

}
