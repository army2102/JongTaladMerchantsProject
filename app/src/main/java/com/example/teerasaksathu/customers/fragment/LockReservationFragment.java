package com.example.teerasaksathu.customers.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teerasaksathu.customers.R;
import com.example.teerasaksathu.customers.activity.MainActivity;
import com.example.teerasaksathu.customers.fragment.dialog.LockMaximumReservedDialogFragment;
import com.example.teerasaksathu.customers.fragment.dialog.LockReservedDialogFragment;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LockReservationFragment extends Fragment implements View.OnClickListener {
    private Button btnReserve;
    private Spinner spProductType;
    private Spinner spLock;
    private ImageView ivRefresh;

    private String lockNameForDelete;
    private String marketName = "RMUTT Walking Street";
    private String dataLock;
    private String timeDate;
    private String username;


    private TextView tvA1;
    private TextView tvA2;
    private TextView tvA3;
    private TextView tvB1;
    private TextView tvB2;
    private TextView tvB3;
    private TextView tvC1;
    private TextView tvC2;
    private TextView tvC3;
    private TextView tvC4;
    private TextView tvC5;
    private TextView tvD1;
    private TextView tvD2;
    private TextView tvD3;
    private TextView tvD4;
    private TextView tvD5;
    private TextView tvD6;
    private TextView tvD7;
    private TextView tvD8;
    private TextView tvD9;
    private TextView tvDate;


    public LockReservationFragment() {
        super();
    }

    public static LockReservationFragment newInstance() {
        LockReservationFragment fragment = new LockReservationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lock_reservation, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here

        timeDate = String.valueOf(timeDate());


        username = MainActivity.intentUsername.getStringExtra("username");

        RefreshMarketlocks refreshMarketlocks = new RefreshMarketlocks();
        refreshMarketlocks.execute();

        loadLockname loadLockname = new loadLockname();
        loadLockname.execute();

        loadProductType loadProductType = new loadProductType();
        loadProductType.execute();


        btnReserve = rootView.findViewById(R.id.btnReserve);
        spProductType = rootView.findViewById(R.id.spProductType);
        ivRefresh = rootView.findViewById(R.id.ivRefresh);
        spLock = rootView.findViewById(R.id.spLock);
        tvDate = rootView.findViewById(R.id.date);
        tvDate.setText(timeDate);
        tvA1 = rootView.findViewById(R.id.tvA1);
        tvA2 = rootView.findViewById(R.id.tvA2);
        tvA3 = rootView.findViewById(R.id.tvA3);
        tvB1 = rootView.findViewById(R.id.tvB1);
        tvB2 = rootView.findViewById(R.id.tvB2);
        tvB3 = rootView.findViewById(R.id.tvB3);
        tvC1 = rootView.findViewById(R.id.tvC1);
        tvC2 = rootView.findViewById(R.id.tvC2);
        tvC3 = rootView.findViewById(R.id.tvC3);
        tvC4 = rootView.findViewById(R.id.tvC4);
        tvC5 = rootView.findViewById(R.id.tvC5);
        tvD1 = rootView.findViewById(R.id.tvD1);
        tvD2 = rootView.findViewById(R.id.tvD2);
        tvD3 = rootView.findViewById(R.id.tvD3);
        tvD4 = rootView.findViewById(R.id.tvD4);
        tvD5 = rootView.findViewById(R.id.tvD5);
        tvD6 = rootView.findViewById(R.id.tvD6);
        tvD7 = rootView.findViewById(R.id.tvD7);
        tvD8 = rootView.findViewById(R.id.tvD8);
        tvD9 = rootView.findViewById(R.id.tvD9);

        ivRefresh.setOnClickListener(this);
        tvA1.setOnClickListener(this);
        tvA2.setOnClickListener(this);
        tvA3.setOnClickListener(this);
        tvB1.setOnClickListener(this);
        tvB2.setOnClickListener(this);
        tvB3.setOnClickListener(this);
        tvC1.setOnClickListener(this);
        tvC2.setOnClickListener(this);
        tvC3.setOnClickListener(this);
        tvC4.setOnClickListener(this);
        tvC5.setOnClickListener(this);
        tvD1.setOnClickListener(this);
        tvD2.setOnClickListener(this);
        tvD3.setOnClickListener(this);
        tvD4.setOnClickListener(this);
        tvD5.setOnClickListener(this);
        tvD6.setOnClickListener(this);
        tvD7.setOnClickListener(this);
        tvD8.setOnClickListener(this);
        tvD9.setOnClickListener(this);
        btnReserve.setOnClickListener(this);


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
        if (view == btnReserve) {
            String lockName = spLock.getSelectedItem().toString().trim();
            String productType = spProductType.getSelectedItem().toString().trim();

            if (lockName.equals("None")) {
                Toast.makeText(getActivity(), "Please select lock", Toast.LENGTH_LONG).show();
            } else {
                ReserveLock reserveLock = new ReserveLock();
                reserveLock.execute(username, marketName, lockName, productType, timeDate);

                loadLockname loadLockname = new loadLockname();
                loadLockname.execute();


            }


        } else if (view == ivRefresh) {


            RefreshMarketlocks refreshMarketlocks = new RefreshMarketlocks();
            refreshMarketlocks.execute();

        } else if (view == tvA1) {
            showLockStatus("A1");
        } else if (view == tvA2) {
            showLockStatus("A2");
        } else if (view == tvA3) {
            showLockStatus("A3");
        } else if (view == tvB1) {
            showLockStatus("B1");
        } else if (view == tvB2) {
            showLockStatus("B2");
        } else if (view == tvB3) {
            showLockStatus("B3");
        } else if (view == tvC1) {
            showLockStatus("C1");
        } else if (view == tvC2) {
            showLockStatus("C2");
        } else if (view == tvC3) {
            showLockStatus("C3");
        } else if (view == tvC4) {
            showLockStatus("C4");
        } else if (view == tvC5) {
            showLockStatus("C5");
        } else if (view == tvD1) {
            showLockStatus("D1");
        } else if (view == tvD2) {
            showLockStatus("D2");
        } else if (view == tvD3) {
            showLockStatus("D3");
        } else if (view == tvD4) {
            showLockStatus("D4");
        } else if (view == tvD5) {
            showLockStatus("D5");
        } else if (view == tvD6) {
            showLockStatus("D6");
        } else if (view == tvD7) {
            showLockStatus("D7");
        } else if (view == tvD8) {
            showLockStatus("D8");
        } else if (view == tvD9) {
            showLockStatus("D9");
        }
    }

    private void setLockStatus(String lockName, Integer colorID) {
        switch (lockName) {
            case "A1":
                tvA1.setBackgroundResource(colorID);
                break;
            case "A2":
                tvA2.setBackgroundResource(colorID);
                break;
            case "A3":
                tvA3.setBackgroundResource(colorID);
                break;
            case "B1":
                tvB1.setBackgroundResource(colorID);
                break;
            case "B2":
                tvB2.setBackgroundResource(colorID);
                break;
            case "B3":
                tvB3.setBackgroundResource(colorID);
                break;
            case "C1":
                tvC1.setBackgroundResource(colorID);
                break;
            case "C2":
                tvC2.setBackgroundResource(colorID);
                break;
            case "C3":
                tvC3.setBackgroundResource(colorID);
                break;
            case "C4":
                tvC4.setBackgroundResource(colorID);
                break;
            case "C5":
                tvC5.setBackgroundResource(colorID);
                break;
            case "D1":
                tvD1.setBackgroundResource(colorID);
                break;
            case "D2":
                tvD2.setBackgroundResource(colorID);
                break;
            case "D3":
                tvD3.setBackgroundResource(colorID);
                break;
            case "D4":
                tvD4.setBackgroundResource(colorID);
                break;
            case "D5":
                tvD5.setBackgroundResource(colorID);
                break;
            case "D6":
                tvD6.setBackgroundResource(colorID);
                break;
            case "D7":
                tvD7.setBackgroundResource(colorID);
                break;
            case "D8":
                tvD8.setBackgroundResource(colorID);
                break;
            case "D9":
                tvD9.setBackgroundResource(colorID);
                break;


        }
    }

    private void showLockStatus(String lockName) {
        lockNameForDelete = lockName;
        loadLockInformation lockInformation = new loadLockInformation();
        lockInformation.execute(lockName, marketName, timeDate);
    }


    private class ReserveLock extends AsyncTask<String, Void, String> {
        public static final String URL = "http://www.jongtalad.com/doc/lock_reservation.php";

        @Override
        protected String doInBackground(String... values) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("username", values[0])
                    .add("marketName", values[1])
                    .add("lockName", values[2])
                    .add("productTypeName", values[3])
                    .add("saleDate", values[4])
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
            Log.d("ReserveLock", "onPostExecute: " + s);
            if (s.trim().equals("1")) {
                FirebaseMessaging.getInstance().subscribeToTopic("lockReserved");
                setLockStatus(spLock.getSelectedItem().toString().trim(), R.color.lockStatusOuccupied);
                RefreshMarketlocks refreshMarketlocks = new RefreshMarketlocks();
                refreshMarketlocks.execute();
                LockReservedDialogFragment lockReserved = new LockReservedDialogFragment();
                lockReserved.show(getFragmentManager(), "lockReservedDialog");
            } else if (s.trim().equals("2")) {

                LockMaximumReservedDialogFragment lockMaximumReserved = new LockMaximumReservedDialogFragment();
                lockMaximumReserved.show(getFragmentManager(), "lockMaximumReservedDialogFragment");

            } else {
                Toast.makeText(getActivity(), "เกิดข้อผิดพลาด", Toast.LENGTH_LONG).show();

            }
        }
    }

    private class RefreshMarketlocks extends AsyncTask<Void, Void, String> {

        private static final String URLstatusLock = "http://www.jongtalad.com/doc/load_lock_status.php";


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            Log.d("Json", "=>" + s);
            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {


                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    dataLock = jsonObject.getString("name");
                    setLockStatus(dataLock, R.color.lockStatusOuccupied);
//                        Log.d("dataLock", dataLock);


                }


            } catch (Exception e) {
//                Log.d("Erorr", "e onPost ==>" + e.toString());
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {


            try {


                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                RequestBody requestBody = new FormBody.Builder()
                        .add("reservationStatus", "2")
                        .add("saleDate", timeDate)
                        .add("marketName", marketName)
                        .build();
                Request request = builder.url(URLstatusLock).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {

//                Log.d("Dpost", "=>" + e);

                return null;
            }


        }
    }

    private class loadLockname extends AsyncTask<Void, Void, String> {
        public static final String URL = "http://www.jongtalad.com/doc/load_lock_status.php";

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("reservationStatus", "1")
                    .add("saleDate", timeDate)
                    .add("marketName", marketName)
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
            String[] lockList;
            try {
                JSONArray jsonArray = new JSONArray(s);
                lockList = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    lockList[i] = (jsonObject.getString("name"));
                }

            } catch (JSONException e) {
                lockList = new String[1];
                lockList[0] = "None";

                e.printStackTrace();
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner_view, lockList);
            adapter.setDropDownViewResource(R.layout.custom_spinner_drop_down);
            spLock.setAdapter(adapter);
        }
    }

    private class loadProductType extends AsyncTask<Void, Void, String> {
        public static final String URL = "http://www.jongtalad.com/doc/loadProductType.php";

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(URL)
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
            String[] productTypeList;
            try {


                JSONArray jsonArray = new JSONArray(s);

                productTypeList = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    productTypeList[i] = jsonObject.getString("name");

                }
            } catch (JSONException e) {
                productTypeList = new String[1];
                productTypeList[0] = "none";
                e.printStackTrace();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner_view, productTypeList);
            adapter.setDropDownViewResource(R.layout.custom_spinner_drop_down);
            spProductType.setAdapter(adapter);
        }
    }

    private class loadLockInformation extends AsyncTask<String, Void, String> {
        public static final String URL = "http://www.jongtalad.com/doc/load_lock_information.php";


        @Override
        protected String doInBackground(String... values) {
            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("lockName", values[0])
                    .add("marketName", values[1])
                    .add("saleDate", values[2])
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
//             Log.d("data", s);
            String lockName = null;
            String name = "null";

            String phonenumber = "null";
            String productType = "null";
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    lockName = jsonObject.getString("lockName");
                    name = jsonObject.getString("merchantName");
                    phonenumber = jsonObject.getString("phonenumber");
                    productType = jsonObject.getString("productType");

                }

            } catch (JSONException e) {
                e.printStackTrace();
//                Log.d("Error -->", e.getMessage());
            }
            if (lockName != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("รายละเอียดล็อค")
                        .setMessage(lockName + "\n" +
                                name + " " +
                                phonenumber + "\n" +
                                productType + "\n")
                        .setNegativeButton("ปิดหน้าจอ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        }
    }

    private class cancelLockReservation extends AsyncTask<String, Void, String> {
        public static final String URL = "http://www.jongtalad.com/doc/cancel_lock_reservation.php";

        @Override
        protected String doInBackground(String... values) {
            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("lockName", values[0])
                    .add("marketName", values[1])
                    .add("saleDate", values[2])
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
            setLockStatus(lockNameForDelete, R.color.lockStatausNotOuccupied);
            loadLockname loadLockname = new loadLockname();
            loadLockname.execute();
        }
    }

    private Date timeDate() {

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        return date;

    }
}
