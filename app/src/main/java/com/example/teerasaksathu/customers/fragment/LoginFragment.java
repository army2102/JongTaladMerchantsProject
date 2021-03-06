package com.example.teerasaksathu.customers.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teerasaksathu.customers.R;
import com.example.teerasaksathu.customers.activity.MainActivity;
import com.example.teerasaksathu.customers.activity.RegisterActivity;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginFragment extends Fragment implements View.OnClickListener {

    private Button btnRegisterPage;
    private Button btnLoginPage;
    private TextView tvForgotPassword;
    private EditText etUsername;
    private EditText etPassword;
    private String username;
    private String password;

    public LoginFragment() {
        super();
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here



        btnRegisterPage = rootView.findViewById(R.id.btnRegisterPage);
        btnLoginPage = rootView.findViewById(R.id.btnLoginPage);
        tvForgotPassword = rootView.findViewById(R.id.tvForgotPassword);
        etUsername = rootView.findViewById(R.id.etUsername);
        etPassword = rootView.findViewById(R.id.etPassword);


        btnRegisterPage.setOnClickListener(this);
        btnLoginPage.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnLoginPage) {
            if (checkLogin()) {
                username = etUsername.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                Login login = new Login();
                login.execute(username, password);

            } else {
                Toast.makeText(getActivity(), "โปรดกรอก Username และ Password", Toast.LENGTH_SHORT).show();
            }

        } else if (view == btnRegisterPage) {
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
        }
    }

    private boolean checkLogin() {
        if (etUsername.length() == 0 || etPassword.length() == 0)
            return false;
        else
            return true;
    }

    private class Login extends AsyncTask<String, Void, String> {
        private static final String URL = "http://www.jongtalad.com/doc/login_merchants.php";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getActivity(), "Loading...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... values) {
            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("username", values[0])
                    .add("password", values[1])
                    .build();

            Request.Builder builder = new Request.Builder();
            Request request = builder
                    .url(URL)
                    .post(requestBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return "Not Success - code : " + response.code();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error - " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Post", "==>" + s);
            //TODO Change .trim() get it out
            if (s.trim().equals("1")) {
                FirebaseMessaging.getInstance().subscribeToTopic("logined");
                Toast.makeText(getActivity(), "Login สำเร็จ", Toast.LENGTH_SHORT).show();
                SharedPreferences prefs = getActivity().getSharedPreferences("user_token", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("logined", username);
                editor.apply();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                getActivity().finish();
            } else {
                Toast.makeText(getActivity(), "Username หรือ Password ผิด", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
