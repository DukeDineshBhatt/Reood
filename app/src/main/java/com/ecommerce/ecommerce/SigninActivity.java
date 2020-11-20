package com.ecommerce.ecommerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SigninActivity extends AppCompatActivity {

    TextView btncontinue;
    SignInButton signInButton;

    private static final int RC_SIGN_IN = 234;
    private static final String TAG = "simplifiedcoding";
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;

    private ProgressBar progressBar;
    TextView txtLogin;
    EditText firstName, lastName, phone, email, password;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_signin);

        signInButton = findViewById(R.id.sign_in_button);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        txtLogin = findViewById(R.id.txtlogin);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btncontinue = findViewById(R.id.btncontinue);
        progressBar = findViewById(R.id.progressbar);
        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerNewUser();
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SigninActivity.this, WelcomeActivity.class);
                startActivity(intent);

            }
        });

        TextView textView = (TextView) signInButton.getChildAt(0);
        textView.setText("Google");

    }


    private void registerNewUser() {
        progressBar.setVisibility(View.VISIBLE);

        final String emailTxt, passwordTxt, phoneTxt, firstTxt, lastTxt, PhoneTxt;
        emailTxt = email.getText().toString();
        passwordTxt = password.getText().toString();
        firstTxt = firstName.getText().toString();
        lastTxt = lastName.getText().toString();
        phoneTxt = phone.getText().toString();

        if (TextUtils.isEmpty(firstTxt)) {
            Toast.makeText(getApplicationContext(), "Please enter first name...", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (TextUtils.isEmpty(lastTxt)) {
            Toast.makeText(getApplicationContext(), "Please enter last name...", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(phoneTxt)) {
            Toast.makeText(getApplicationContext(), "Please enter phone number!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(emailTxt)) {
            Toast.makeText(getApplicationContext(), "Please enter email!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(passwordTxt)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailTxt, passwordTxt)
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

                            SiginRequest body = new SiginRequest();
                            body.setUserId(uid);
                            body.setEmail(emailTxt);
                            body.setFirstName(firstTxt);
                            body.setLastName(lastTxt);
                            body.setPhoneNumber(phoneTxt);

                            Call<SigninBean> call = cr.sigin(body);
                            call.enqueue(new Callback<SigninBean>() {
                                @Override
                                public void onResponse(Call<SigninBean> call, Response<SigninBean> response) {

                                    boolean a = true;
                                    if (response.body().getStatus().equals(a)) {
                                        Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();

                                        Log.d("DINESH","Dinesh");
                                        SharedPreferences mPrefs = getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = mPrefs.edit();
                                        editor.putString("uid", uid);
                                        editor.putString("token", response.body().getData().get(0).getToken());
                                        editor.commit();

                                        progressBar.setVisibility(View.GONE);
                                        Intent intent = new Intent(SigninActivity.this, WelcomeActivity.class);
                                        startActivity(intent);
                                    }

                                }

                                @Override
                                public void onFailure(Call<SigninBean> call, Throwable t) {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //authenticating with firebase
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(SigninActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            uid = user.getUid();
                            String name = user.getDisplayName();
                            Log.d(TAG, "signInWithCredential:success: currentUser: " + user.getEmail());
                            Log.d("DINESH", name);

                            /*SharedPreferences mPrefs = getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = mPrefs.edit();
                            editor.putString("uid", uid);
                            editor.putBoolean("is_logged_before", true);
                            editor.commit();

                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                            intent.putExtra("uid",uid);
                            startActivity(intent);
                            finishAffinity();*/

                        } else {
                            // If sign in fails, display a message to the user.

                            Log.d(TAG, "signInWithCredential:failure", task.getException());

                            showToastMessage("Login failed:" + task.getException());

                        }
                    }
                });
    }

    private void showToastMessage(String message) {
        Toast.makeText(SigninActivity.this, message, Toast.LENGTH_LONG).show();
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}