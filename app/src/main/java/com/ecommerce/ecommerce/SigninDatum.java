package com.ecommerce.ecommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SigninDatum {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;
    @SerializedName("user_type")
    @Expose
    private Integer userType;
    @SerializedName("existing_user")
    @Expose
    private Boolean existingUser;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Boolean getExistingUser() {
        return existingUser;
    }

    public void setExistingUser(Boolean existingUser) {
        this.existingUser = existingUser;
    }

}
