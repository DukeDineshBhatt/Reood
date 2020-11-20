package com.ecommerce.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ecommerce.ecommerce.ProfilePOJO.ProfileBean;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.facebook.FacebookSdk.getApplicationContext;

public class EditProfile extends AppCompatActivity {

    TextView nameTv, phoneTv, emailTv, name1Tv, logout;
    String token;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nameTv = findViewById(R.id.name);
        emailTv = findViewById(R.id.email);
        phoneTv = findViewById(R.id.phone);
        name1Tv = findViewById(R.id.name1);
        progressBar = findViewById(R.id.progressbar);

        SharedPreferences shared = getSharedPreferences("myAppPrefs", MODE_PRIVATE);

        token = (shared.getString("token", ""));


        progressBar.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.HEADERS);
        logging.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().writeTimeout(1000, TimeUnit.SECONDS).readTimeout(1000, TimeUnit.SECONDS).connectTimeout(1000, TimeUnit.SECONDS).addInterceptor(logging).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<ProfileBean> call = cr.getProfile("Basic " + token);
        call.enqueue(new Callback<ProfileBean>() {
            @Override
            public void onResponse(Call<ProfileBean> call, Response<ProfileBean> response) {

                boolean a = true;
                if (response.body().getStatus().equals(a)) {

                    nameTv.setText(response.body().getData().get(0).getFirstName() + " " + response.body().getData().get(0).getLastName());
                    phoneTv.setText(response.body().getData().get(0).getPhoneNumber());
                    emailTv.setText(response.body().getData().get(0).getEmail());
                    name1Tv.setText(response.body().getData().get(0).getFirstName() + " " + response.body().getData().get(0).getLastName());
                    progressBar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<ProfileBean> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

       /* progressBar.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.HEADERS);
        logging.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().writeTimeout(1000, TimeUnit.SECONDS).readTimeout(1000, TimeUnit.SECONDS).connectTimeout(1000, TimeUnit.SECONDS).addInterceptor(logging).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<ProfileBean> call = cr.getProfile("Basic " + token);
        call.enqueue(new Callback<ProfileBean>() {
            @Override
            public void onResponse(Call<ProfileBean> call, Response<ProfileBean> response) {

                boolean a = true;
                if (response.body().getStatus().equals(a)) {

                    nameTv.setText(response.body().getData().get(0).getFirstName() + " " + response.body().getData().get(0).getLastName());
                    phoneTv.setText(response.body().getData().get(0).getPhoneNumber());
                    emailTv.setText(response.body().getData().get(0).getEmail());
                    name1Tv.setText(response.body().getData().get(0).getFirstName() + " " + response.body().getData().get(0).getLastName());
                    progressBar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<ProfileBean> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });*/
    }
}
