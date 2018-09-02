package com.example.teerasaksathu.customers.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.teerasaksathu.customers.R;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button conflButtonrm;
    EditText editTextID_card, editText_Name, editText_Surname, editText_Phone,
            editText_Username, editText_Password, editText_conflrmPassWord;
    String merchantIdCard, merchantName, merchantSurname,
            merchantPhonenumber, usernameString, password, confirmPassword;
    private String filePath;
    private StorageReference mStorageRef;
    private Button btnUploadImage;
    private ImageView ivImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.contentContainer, RegisterFragment.newInstance())
//                    .commit();
//        }

        initInstances();
    }

    private void initInstances() {
        ivImg = findViewById(R.id.ivImg);
        conflButtonrm = findViewById(R.id.btncomflrmregister);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        editTextID_card = findViewById(R.id.edidcard);
        editText_Name = findViewById(R.id.edname);
        editText_Surname = findViewById(R.id.edsurname);
        editText_Phone = findViewById(R.id.edphone);
        editText_Username = findViewById(R.id.edusername);
        editText_Password = findViewById(R.id.edpassword);
        editText_conflrmPassWord = findViewById(R.id.edcomfirm);


        conflButtonrm.setOnClickListener(this);
        btnUploadImage.setOnClickListener(this);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
//
//            //หา path รูป
//            Uri uri = data.getData();
//            filePath = myFindPathImage(uri);
//            //result Complete
//
//            //Setup Image to ImageView
//            try {
//                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
//                ivImg.setImageBitmap(bitmap);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }//try
//
//
//        }//if
//    }

//    private String myFindPathImage(Uri uri) {
//
//
//        String strResult = null;
//        String[] strings = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getContentResolver().query(uri, strings, null, null, null);
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
//    }//myFindPathImage


    @Override
    public void onClick(View view) {

        if (view == conflButtonrm) {
            if (InputValidation()) {
                Register register = new Register();
                register.execute(merchantName, merchantSurname, merchantPhonenumber, merchantIdCard, usernameString, password);

                //        if (filePath != null) {
//            Log.d("UploadImage", "Image has a path");
//            mStorageRef = FirebaseStorage.getInstance().getReference();
//            Uri file = Uri.fromFile(new File(filePath));
//            StorageReference riversRef = mStorageRef.child("Merchants/" + usernameString);
//
//
//            riversRef.putFile(file)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            // Get a URL to the uploaded content
//                            Uri pictureUrl = taskSnapshot.getDownloadUrl();
//                            Log.d("UploadImage", "Image uploaded : " + pictureUrl);
//
//                            UploadProfileImage uploadProfileImage = new UploadProfileImage();
//                            uploadProfileImage.execute(usernameString, pictureUrl.toString());
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            // Handle unsuccessful uploads
//                            // ...
//                            Log.d("UploadImage", "Fail to upload image");
//
//                        }
//                    });
//
//        }
            }
        }
//        else if (view == btnUploadImage) {
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_PICK);
//            startActivityForResult(Intent.createChooser(intent, "โปรดเลือกรูป"), 1);
//
//        }

    }

    private boolean InputValidation() {
        int count = 0;
        merchantName = editText_Name.getText().toString().trim();
        if (merchantName.length() == 0) {
            count++;
        }

        merchantSurname = editText_Surname.getText().toString().trim();
        if (merchantSurname.length() == 0) {
            count++;
        }

        usernameString = editText_Username.getText().toString().trim();
        if (usernameString.length() == 0) {
            count++;
        }

        if (count > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Please fill up the form")
                    .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }

        password = editText_Password.getText().toString().trim();
        if (password.length() == 0 || password.length() < 8) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Password must have at less 8 character")
                    .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }

        confirmPassword = editText_conflrmPassWord.getText().toString().trim();
        if (!(confirmPassword.equals(password))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Your password doesn't match")
                    .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }

        merchantPhonenumber = editText_Phone.getText().toString().trim();
        if (merchantPhonenumber.length() == 0 || merchantPhonenumber.length() < 10) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Phonenumber must have 10 character")
                    .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }

        merchantIdCard = editTextID_card.getText().toString().trim();
        if (merchantIdCard.length() == 0 || merchantIdCard.length() < 13) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("ID card must have 13 character")
                    .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }

        return true;
    }

    private class Register extends AsyncTask<String, Void, String> {

        private static final String URL = "https://jongtalad-web-api.herokuapp.com/auth/register/merchant";

        @Override
        protected String doInBackground(String... values) {
            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("merchantName", values[0])
                        .add("merchantSurname", values[1])
                        .add("merchantPhonenumber", values[2])
                        .add("merchantIdCard", values[3])
                        .add("username", values[4])
                        .add("password", values[5])
                        .build();

                Request.Builder builder = new Request
                        .Builder();

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
            } catch (Exception e) {

                Log.d("Register", e.getMessage());
                return e.getMessage();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            int resCode = 404;
            try {
                JSONObject obj = new JSONObject(s);
                resCode = obj.getInt("code");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (resCode == 201) {
                Toast.makeText(RegisterActivity.this, "Register success", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_LONG).show();
            }
        }
    }

//    private class UploadProfileImage extends AsyncTask<String, Void, String> {
//        public static final String URL = "http://www.jongtalad.com/doc/upload_profile_image.php";
//
//
//        @Override
//        protected String doInBackground(String... values) {
//            OkHttpClient okHttpClient = new OkHttpClient();
//            RequestBody requestBody = new FormBody.Builder()
//                    .add("username", values[0])
//                    .add("pictureUrl", values[1])
//                    .build();
//            Request request = new Request.Builder()
//                    .url(URL)
//                    .post(requestBody)
//                    .build();
//            try {
//                Response response = okHttpClient.newCall(request).execute();
//                if (response.isSuccessful()) {
//                    return response.body().string();
//                } else {
//                    return "Not Success - code : " + response.code();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "Error - " + e.getMessage();
//            }
//        }
//    }
}


