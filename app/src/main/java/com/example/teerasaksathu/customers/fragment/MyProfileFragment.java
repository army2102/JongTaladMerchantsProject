package com.example.teerasaksathu.customers.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teerasaksathu.customers.R;
import com.example.teerasaksathu.customers.activity.EditProfileActivity;
import com.example.teerasaksathu.customers.activity.MainActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;


public class MyProfileFragment extends Fragment implements View.OnClickListener {

    private TextView tvName;
    private TextView tvSurname;
    private TextView tvPhonenumber;
    private Button btnEdit;
    private String name;
    private String surname;
    private String phonenumber;
    private String username;
    private String pictureUrl;
    private ImageView ivImg;


    public MyProfileFragment() {
        super();
    }

    public static MyProfileFragment newInstance() {
        MyProfileFragment fragment = new MyProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_myprofile, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        tvName = rootView.findViewById(R.id.tvName);
        tvSurname = rootView.findViewById(R.id.tvSurname);
        tvPhonenumber = rootView.findViewById(R.id.tvPhonenumber);
        btnEdit = rootView.findViewById(R.id.btnEdit);
        ivImg = rootView.findViewById(R.id.ivImg);
//        username = MainActivity.intentUsername.getStringExtra("username");
        loadUserData loadUserData = new loadUserData();
        loadUserData.execute(username);

        btnEdit.setOnClickListener(this);
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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//

//
//
//    }//onActivity
//
//
//    private String myFinndPathImage(Uri uri) {
//
//
//        String strResult = null;
//        String[] strings = {MediaStore.Images.Media.DATA};
//        Cursor cursor = query(uri, strings, null, null, null)
//        if (cursor != null) {
//
//            cursor.moveToFirst();
//            int intIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            strResult = cursor.getString(intIndex);
//
//        } else {
//
//            strResult = uri.getPath();
//
//        }
//        return strResult;
//    }//myFinndPathImage


    public void onClick(View view) {
        if (view == btnEdit) {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }

    }


    private class loadUserData extends AsyncTask<String, Void, String> {
        public static final String URL = "http://www.jongtalad.com/doc/load_user_data.php";


        @Override
        protected String doInBackground(String... values) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("username", values[0])
                    .build();
            Request request = new Request.Builder()
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
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    name = jsonObject.getString("name");
                    surname = jsonObject.getString("surname");
                    phonenumber = jsonObject.getString("phonenumber");
                    pictureUrl = jsonObject.getString("picture_url");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            tvName.setText(name);
            tvSurname.setText(surname);
            tvPhonenumber.setText(phonenumber);

            try {

                Picasso.with(getActivity())
                        .load(pictureUrl)
                        .placeholder(R.drawable.placeholder)
                        .into(ivImg);

            } catch (IllegalArgumentException e) {

                Picasso.with(getActivity())
                        .load(R.drawable.pc)
                        .into(ivImg);
            }


        }
    }
}
