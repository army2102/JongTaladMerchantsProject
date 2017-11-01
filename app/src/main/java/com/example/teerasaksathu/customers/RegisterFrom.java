package com.example.teerasaksathu.customers;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterFrom extends AppCompatActivity implements View.OnClickListener {

    Button conflButtonrm;
    EditText editTextID_card,editText_Name,editText_Surname,editText_Phone,editText_Username,editText_Password, editText_conflrmPassWord;
    String ID_CardString,nameString,sruNameString,phoneString,usernameString,passwordString, conlrmPassWordString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_from);

        bingVal();
        conflButtonrm.setOnClickListener(this);

    }

    private void bingVal() {

        conflButtonrm = (Button) findViewById(R.id.btncomflrmregister);
        editTextID_card = (EditText) findViewById(R.id.edidcard);
        editText_Name = (EditText) findViewById(R.id.edname);
        editText_Surname = (EditText) findViewById(R.id.edsurname);
        editText_Phone = (EditText) findViewById(R.id.edphone);
        editText_Username = (EditText) findViewById(R.id.edusername);
        editText_Password = (EditText) findViewById(R.id.edpassword);
        editText_conflrmPassWord = (EditText) findViewById(R.id.edcomfirm);

    }

    @Override
    public void onClick(View view) {

        if (view == conflButtonrm) {

            checkvalue();


        }
    }

    private void checkvalue() {

        ID_CardString = editTextID_card.getText().toString().trim();
        nameString = editText_Name.getText().toString().trim();
        sruNameString = editText_Surname.getText().toString().trim();
        phoneString = editText_Phone.getText().toString().trim();
        usernameString = editText_Username.getText().toString().trim();
        passwordString = editText_Password.getText().toString().trim();
        conlrmPassWordString = editText_conflrmPassWord.getText().toString().trim();


        if (ID_CardString.length() == 0 || nameString.length() == 0 || sruNameString.length() == 0 || phoneString.length() == 0 || usernameString.length() == 0 || passwordString.length() == 0 || conlrmPassWordString.length() == 0) {
            Toast.makeText(getApplicationContext(), "กรอกให้ครบทุกช่อง", Toast.LENGTH_SHORT).show();
        } else {
            if (ID_CardString.length() != 13) {
                Toast.makeText(getApplicationContext(), "กรอกเลขบัตรประจำตัวประชาชนให้ครบ 13 หลัก", Toast.LENGTH_SHORT).show();
            } else {
                if (passwordString.length() < 8) {
                    Toast.makeText(getApplicationContext(), "กรอก password อย่างน้อย 8 ตัว", Toast.LENGTH_SHORT).show();
                } else {
                if (passwordString.equals(conlrmPassWordString)) {

                    if (phoneString.length() != 10) {
                        Toast.makeText(getApplicationContext(), "กรอกหมายเลขโทรศัพท์ให้ครบ 10 หลัก", Toast.LENGTH_SHORT).show();
                    } else {

                        Log.d("name", nameString);
                        Log.d("id", ID_CardString);
                        Log.d("sur", sruNameString);
                        Log.d("user", usernameString);
                        Log.d("passwod", passwordString);
                        Log.d("phone", phoneString);

                        upDataToDB upDataToDB = new upDataToDB();
                        upDataToDB.execute();


                    }
                } else {
                    Toast.makeText(getApplicationContext(), "password ไม่ตรงกัน", Toast.LENGTH_SHORT).show();
                }//password กับ comflempassword ต้องตรงกัน
            }//passwordต้องไม่น้อยกว่า 8
         }//บัตรประชาชนต้องมี 13 หลัก
        }//กรอกให้ครบ


    }

    private class upDataToDB extends AsyncTask<Void, Void, String> {

        private static final String URLregister = "http://www.jongtalad.com/doc/register_merchants.php";

        @Override
        protected String doInBackground(Void... voids) {



            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("id_card", ID_CardString)
                        .add("name", nameString)
                        .add("surname", sruNameString)
                        .add("phone", phoneString)
                        .add("username", usernameString)
                        .add("password", passwordString)
                        .build();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(URLregister).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {

                Log.d("upDataToDB", e.getMessage());
                return e.getMessage();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("POST", s);
            if (s.equals("Done")) {
                Toast.makeText(getApplicationContext(), "เสร็จสิ้น", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterFrom.this, MainActivity.class);
                startActivity(intent);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

}