package com.ecommerce.ecommerce;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Profile extends Fragment {

    TextView nameTv, phoneTv, emailTv, name1Tv,logout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.profile_frgment, container, false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String uid = prefs.getString("uid", "null");
        String first = prefs.getString("first", "null");
        String last = prefs.getString("last", "null");
        String phone = prefs.getString("phone", "null");
        String email = prefs.getString("email", "null");
        String password = prefs.getString("password", "null");

        nameTv = root.findViewById(R.id.name);
        emailTv = root.findViewById(R.id.email);
        phoneTv = root.findViewById(R.id.phone);
        name1Tv = root.findViewById(R.id.name1);
        logout = root.findViewById(R.id.logout);

        nameTv.setText(first);
        phoneTv.setText(phone);
        emailTv.setText(email);
        name1Tv.setText(first);


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


                                SharedPreferences mPrefs = getActivity().getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = mPrefs.edit();
                                editor1.remove("user_id");

                                editor.commit();
                                editor1.commit();

                                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                                startActivity(intent);
                                getActivity().finish();

                            }
                        }).create().show();

            }
        });

        return root;

    }

}
