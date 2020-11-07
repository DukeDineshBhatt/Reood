package com.ecommerce.ecommerce;

import com.ecommerce.ecommerce.CategoryPOJO.CategoryBean;
import com.ecommerce.ecommerce.LoginPOJO.LoginBean;
import com.ecommerce.ecommerce.LoginPOJO.LoginRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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


}
