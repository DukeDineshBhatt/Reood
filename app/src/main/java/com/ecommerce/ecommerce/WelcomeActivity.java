package com.ecommerce.ecommerce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ecommerce.ecommerce.LoginPOJO.LoginBean;
import com.ecommerce.ecommerce.LoginPOJO.LoginRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WelcomeActivity extends AppCompatActivity {
    TextView btncontinue;
    EditText emailET, passwordET;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    String uid;
    public static final String PREF_LOGIN = "LOGIN_PREF";
    public static final String KEY_CREDENTIALS = "LOGIN_CREDENTIALS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();

        btncontinue = findViewById(R.id.btncontinue);
        emailET = findViewById(R.id.email);
        passwordET = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressbar);

        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUserAccount();

            }
        });
    }

    private void loginUserAccount() {
        progressBar.setVisibility(View.VISIBLE);

        String email, password;
        email = emailET.getText().toString();
        password = passwordET.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            uid = currentFirebaseUser.getUid();

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

                            LoginRequest body = new LoginRequest();
                            body.setUserId(uid);

                            Call<LoginBean> call = cr.login(body);
                            call.enqueue(new Callback<LoginBean>() {
                                @Override
                                public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {

                                    String a = response.body().getStatus().toString();

                                    if (a.equals("true")) {

                                       /* SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(WelcomeActivity.this).edit();
                                        prefEditor.putString("first", firstTxt);
                                        prefEditor.putString("last", lastTxt);
                                        prefEditor.putString("phone", phoneTxt);
                                        prefEditor.putString("email", emailTxt);
                                        prefEditor.putString("password", passwordTxt);
                                        prefEditor.putString("uid", uid);
                                        prefEditor.apply();
*/
                                        SharedPreferences mPrefs = getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = mPrefs.edit();
                                        editor.putString("uid", uid);
                                        editor.putBoolean("is_logged_before", true);
                                        editor.commit();

                                        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                        intent.putExtra("uid", uid);
                                        startActivity(intent);
                                        finishAffinity();
                                    }

                                }

                                @Override
                                public void onFailure(Call<LoginBean> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Registration failed, Try again", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            });

                        } else {
                            String localizedMessage = task.getException().getLocalizedMessage();
                            Toast.makeText(getApplicationContext(), localizedMessage, Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

}
