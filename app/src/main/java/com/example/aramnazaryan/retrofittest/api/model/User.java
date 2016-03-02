package com.example.aramnazaryan.retrofittest.api.model;

import com.google.gson.annotations.SerializedName;
public class User{

    @SerializedName("key")
    public String key;
    @SerializedName("email")
    public String email;
    @SerializedName("provider")
    public String provider;
    @SerializedName("registered")
    public boolean registered;
    @SerializedName("mature")
    public boolean mature;
    // @SerializedName("balance")
    // public int balance = 0;
	@SerializedName("username_changed")
    public boolean usernameChanged = false;


    public final static User emptyUser = new User();

    static {
        emptyUser.key = "";
        emptyUser.email = "";
        emptyUser.provider = "";
        emptyUser.registered = false;
    }

    public User() {

    }



}
