package com.ecommerce.ecommerce;

import com.ecommerce.ecommerce.CategoryPOJO.CategoryBean;
import com.ecommerce.ecommerce.LoginPOJO.LoginBean;
import com.ecommerce.ecommerce.LoginPOJO.LoginRequest;
import com.ecommerce.ecommerce.ProfilePOJO.ProfileBean;
import com.ecommerce.ecommerce.RestaurantPOJO.RestaurantBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface AllApiIneterface {

    @POST("auth/user_account/")
    Call<SigninBean> sigin(
            @Body SiginRequest body
    );

    @POST("auth/login/")
    Call<LoginBean> login(
            @Body LoginRequest body
    );

    @GET("master/category")
    Call<CategoryBean> category();

    @GET("master/restaurant")
    Call<RestaurantBean> getExampleMethod(@Header("Authorization") String token);

    @GET("auth/login/")
    Call<ProfileBean> getProfile(@Header("Authorization") String token);
}
