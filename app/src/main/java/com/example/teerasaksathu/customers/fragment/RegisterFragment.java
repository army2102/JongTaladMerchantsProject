package com.example.teerasaksathu.customers.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teerasaksathu.customers.R;
import com.example.teerasaksathu.customers.activity.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RegisterFragment extends Fragment implements View.OnClickListener {

    Button conflButtonrm;
    EditText editTextID_card, editText_Name, editText_Surname, editText_Phone,
            editText_Username, editText_Password, editText_conflrmPassWord;
    String merchantIdCard, merchantName, merchantSurname,
            merchantPhonenumber, username, password, confirmPassword;
    private String filePath;
    private StorageReference mStorageRef;
    private Button btnUploadImage;

    public RegisterFragment() {
        super();
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        conflButtonrm = rootView.findViewById(R.id.btncomflrmregister);
        btnUploadImage = rootView.findViewById(R.id.btnUploadImage);
        editTextID_card = rootView.findViewById(R.id.edidcard);
        editText_Name = rootView.findViewById(R.id.edname);
        editText_Surname = rootView.findViewById(R.id.edsurname);
        editText_Phone = rootView.findViewById(R.id.edphone);
        editText_Username = rootView.findViewById(R.id.edusername);
        editText_Password = rootView.findViewById(R.id.edpassword);
        editText_conflrmPassWord = rootView.findViewById(R.id.edcomfirm);


        conflButtonrm.setOnClickListener(this);
        btnUploadImage.setOnClickListener(this);
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
        if (view == conflButtonrm) {
            checkvalue();
        }
//        else if (view == btnUploadImage) {
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_PICK);
//            startActivityForResult(Intent.createChooser(intent, "โปรดเลือกรูป"), 1);
//
//        }
    }

    private void checkvalue() {
        merchantName = editText_Name.getText().toString().trim();
        merchantSurname = editText_Surname.getText().toString().trim();
        merchantPhonenumber = editText_Phone.getText().toString().trim();
        merchantIdCard = editTextID_card.getText().toString().trim();
        username = editText_Username.getText().toString().trim();
        password = editText_Password.getText().toString().trim();
        confirmPassword = editText_conflrmPassWord.getText().toString().trim();

        if (merchantIdCard.length() == 0 || merchantName.length() == 0 || merchantSurname.length() == 0 || merchantPhonenumber.length() == 0 || username.length() == 0 || password.length() == 0 || confirmPassword.length() == 0) {
            Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
        } else {
            if (merchantIdCard.length() != 13) {
                Toast.makeText(getActivity(), "กรอกเลขบัตรประจำตัวประชาชนให้ครบ 13 หลัก", Toast.LENGTH_SHORT).show();
            } else {
                if (password.length() < 8) {
                    Toast.makeText(getActivity(), "กรอก password อย่างน้อย 8 ตัว", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(confirmPassword)) {

                        if (merchantPhonenumber.length() != 10) {
                            Toast.makeText(getActivity(), "กรอกหมายเลขโทรศัพท์ให้ครบ 10 หลัก", Toast.LENGTH_SHORT).show();
                        } else {
                            Register register = new Register();
                            register.execute(merchantIdCard, merchantName, merchantSurname, merchantPhonenumber, username, password);

                            if (filePath != null) {
                                Log.d("UploadImage", "Image has a path");
                                mStorageRef = FirebaseStorage.getInstance().getReference();
                                Uri file = Uri.fromFile(new File(filePath));
                                StorageReference riversRef = mStorageRef.child("Merchants/" + username);

                                riversRef.putFile(file)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                // Get a URL to the uploaded content
                                                Uri pictureUrl = taskSnapshot.getDownloadUrl();
                                                Log.d("UploadImage", "Image uploaded : " + pictureUrl);

                                                UploadProfileImage uploadProfileImage = new UploadProfileImage();
                                                uploadProfileImage.execute(username, pictureUrl.toString());
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                // Handle unsuccessful uploads
                                                // ...
                                                Log.d("UploadImage", "Fail to upload image");

                                            }
                                        });

                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), "password ไม่ตรงกัน", Toast.LENGTH_SHORT).show();
                    }//password กับ comflempassword ต้องตรงกัน
                }//passwordต้องไม่น้อยกว่า 8
            }//บัตรประชาชนต้องมี 13 หลัก
        }//กรอกให้ครบ


    }

    private class Register extends AsyncTask<String, Void, String> {

        private static final String URLregister = "http://www.jongtalad.com/doc/register_merchants.php";

        @Override
        protected String doInBackground(String... values) {


            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("idCard", values[0])
                        .add("name", values[1])
                        .add("surname", values[2])
                        .add("phonenumber", values[3])
                        .add("username", values[4])
                        .add("password", values[5])
                        .build();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(URLregister).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {

                Log.d("Register", e.getMessage());
                return e.getMessage();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Register result", s);
            if (s.equals("1")) {
                Toast.makeText(getActivity(), "สมัครสมาชิก สำเร็จ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "Username นี้มีอยู่ในระบบอยู่แล้ววกรุณาใช้ Username อื่น", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class UploadProfileImage extends AsyncTask<String, Void, String> {
        public static final String URL = "http://www.jongtalad.com/doc/upload_profile_image.php";


        @Override
        protected String doInBackground(String... values) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("username", values[0])
                    .add("pictureUrl", values[1])
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
    }
}
