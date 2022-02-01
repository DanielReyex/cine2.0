package com.idat.cine.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.idat.cine.R;
import com.idat.cine.activity.DashboardActivity;

public class LoginFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        final TextInputEditText usernameText = view.findViewById(R.id.username_edit_Text);
        final TextInputEditText passwordText = view.findViewById(R.id.password_edit_text);
        final Button loginButton = view.findViewById(R.id.login_button);
        final Button cancelButton = view.findViewById(R.id.login_cancel_button);
        final ProgressBar loadingBl = view.findViewById(R.id.progress_bar_loading);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();


        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), DashboardActivity.class);
                startActivity(i);
                getActivity().finish();

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBl.setVisibility(View.VISIBLE);
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();

                if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
                    Toast.makeText(getActivity(), "Prease enter your credentials", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    System.out.println(username);
                    System.out.println(password);
                    mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loadingBl.setVisibility(view.GONE);
                                Toast.makeText(getActivity(), "Login successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getActivity(), DashboardActivity.class);
                                startActivity(i);
                                getActivity().finish();

                            } else {
                                loadingBl.setVisibility(view.GONE);
                                Toast.makeText(getActivity(), "Login failure, please try again!", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }

            }
        });



        return view;

    }
}