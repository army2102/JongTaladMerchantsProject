package com.example.teerasaksathu.customers.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teerasaksathu.customers.R;
import com.example.teerasaksathu.customers.fragment.EditProfileFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditProfileActivity extends AppCompatActivity {

 public static Intent intentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, EditProfileFragment.newInstance())
                    .commit();
        }
        initInstances();


    }



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
//            Log.d("MyFrienfV1 ", "Result ==>OK");
//
//            //หา path รูป
//            Uri uri = data.getData();
//            imagePathString = myFinndPathImage(uri);
//            Log.d("MyFrienfV1", "imagePathString ==>" + imagePathString);
//            //result Complete
//
//            //Setup Image to ImageView
//            try {
//                Bitmap bitmap = BitmapFactory.decodeStream(get);
//                imageView.setImageBitmap(bitmap);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }//try
//
//            statusABoolean = false;
//        }//if
//    }

    private void initInstances() {

     intentUsername = getIntent();
    }


}
