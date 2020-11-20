package com.ecommerce.ecommerce;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ecommerce.ecommerce.LoginPOJO.LoginBean;
import com.ecommerce.ecommerce.LoginPOJO.LoginRequest;
import com.ecommerce.ecommerce.ProfilePOJO.ProfileBean;
import com.ecommerce.ecommerce.RestaurantPOJO.RestaurantBean;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class Profile extends Fragment {

    TextView nameTv, phoneTv, emailTv, name1Tv, logout, edit;
    String token;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.profile_frgment, container, false);

       /* SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        token = sharedPref.getString("token", "defaultValue");*/

        SharedPreferences shared = getActivity().getSharedPreferences("myAppPrefs", MODE_PRIVATE);

        token = (shared.getString("token", ""));

        // token = ((Bean) getActivity().getApplication()).getToken();

        Log.d("TOKEN", token);

        nameTv = root.findViewById(R.id.name);
        emailTv = root.findViewById(R.id.email);
        phoneTv = root.findViewById(R.id.phone);
        name1Tv = root.findViewById(R.id.name1);
        logout = root.findViewById(R.id.logout);
        progressBar = root.findViewById(R.id.progressbar);
        edit = root.findViewById(R.id.edit);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getActivity())
                        .setMessage("are you sure want to logout?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {

                                SharedPreferences settings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("hasLoggedIn", false);


                                SharedPreferences mPrefs = getActivity().getSharedPreferences("myAppPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = mPrefs.edit();
                                editor1.remove("user_id");
                                editor1.remove("token");

                                editor.commit();
                                editor1.commit();

                                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                                startActivity(intent);
                                getActivity().finish();

                            }
                        }).create().show();

            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);

            }
        });

        return root;

    }

    @Override
    public void onResume() {
        super.onResume();

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
}
