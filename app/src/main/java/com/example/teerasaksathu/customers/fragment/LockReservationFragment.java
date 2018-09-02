package com.example.teerasaksathu.customers.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teerasaksathu.customers.R;
import com.example.teerasaksathu.customers.activity.LockReservationActivity;
import com.example.teerasaksathu.customers.fragment.dialog.LockMaximumReservedDialogFragment;
import com.example.teerasaksathu.customers.fragment.dialog.LockReservedDialogFragment;
import com.example.teerasaksathu.customers.fragment.dialog.MarketRuleDialogFragment;

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

    private TextView tvMarketName;

    ProgressDialog loadingMarketDataDialog;
    ProgressDialog loadingMarketDetailDialog;
    ProgressDialog loadingRefreshMarketLockDialog;

    // Sent from MainActivity
    private int merchantId;
    private int marketId;
    private String marketName;

    // Use for reserve market lock
    private final int price = 100;
    private int[] marketLockId = {0};
    private String[] marketLockName = {"none"};
    private int[] productTypeId = {0};
    private String[] productTypeName = {"none"};

    private String timeDate;

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
        timeDate = String.valueOf(timeDate());
        merchantId = LockReservationActivity.intent.getIntExtra("merchantId", 0);
        marketId = LockReservationActivity.intent.getIntExtra("marketId", 0);
        marketName = LockReservationActivity.intent.getStringExtra("marketName");

        btnReserve = rootView.findViewById(R.id.btnReserve);
        spProductType = rootView.findViewById(R.id.spProductType);
        ivRefresh = rootView.findViewById(R.id.ivRefresh);
        spLock = rootView.findViewById(R.id.spLock);
        tvDate = rootView.findViewById(R.id.date);
        tvDate.setText(timeDate);
        tvMarketName = rootView.findViewById(R.id.tvMarketName);
        tvMarketName.setText(marketName);
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

        LoadDataToTv loadDataToTv = new LoadDataToTv();
        loadDataToTv.execute();

        loadLockname loadLockname = new loadLockname();
        loadLockname.execute();

        loadProductType loadProductType = new loadProductType();
        loadProductType.execute();

        MarketRuleDialogFragment marketRuleDialogFragment = new MarketRuleDialogFragment();
        marketRuleDialogFragment.show(getFragmentManager(), "marketRuleDialogFragment");
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
            String marketLockName = spLock.getSelectedItem().toString().trim();
            String productTypeName = spProductType.getSelectedItem().toString().trim();

            int getMarketLockId = marketLockId[find(this.marketLockName, marketLockName)];
            int getProductTypeId = productTypeId[find(this.productTypeName, productTypeName)];

            if (marketLockName.equals("None")) {
                Toast.makeText(getActivity(), "Please select lock", Toast.LENGTH_LONG).show();
            } else {
                ReserveLock reserveLock = new ReserveLock();
                reserveLock.execute(String.valueOf(getMarketLockId), String.valueOf(getProductTypeId));

                loadLockname loadLockname = new loadLockname();
                loadLockname.execute();
            }

        } else if (view == ivRefresh) {
            RefreshMarketLock refreshMarketLock = new RefreshMarketLock();
            refreshMarketLock.execute();
        } else if (view == tvA1) {
            showLockDetails(1);
        } else if (view == tvA2) {
            showLockDetails(2);
        } else if (view == tvA3) {
            showLockDetails(3);
        } else if (view == tvB1) {
            showLockDetails(4);
        } else if (view == tvB2) {
            showLockDetails(5);
        } else if (view == tvB3) {
            showLockDetails(6);
        } else if (view == tvC1) {
            showLockDetails(7);
        } else if (view == tvC2) {
            showLockDetails(8);
        } else if (view == tvC3) {
            showLockDetails(9);
        } else if (view == tvC4) {
            showLockDetails(10);
        } else if (view == tvC5) {
            showLockDetails(11);
        } else if (view == tvD1) {
            showLockDetails(12);
        } else if (view == tvD2) {
            showLockDetails(13);
        } else if (view == tvD3) {
            showLockDetails(14);
        } else if (view == tvD4) {
            showLockDetails(15);
        } else if (view == tvD5) {
            showLockDetails(16);
        } else if (view == tvD6) {
            showLockDetails(17);
        } else if (view == tvD7) {
            showLockDetails(18);
        } else if (view == tvD8) {
            showLockDetails(19);
        } else if (view == tvD9) {
            showLockDetails(20);
        }
    }

    private void setLockStatus(int marketLockId, Integer colorID) {
        switch (marketLockId) {
            case 1:
                tvA1.setBackgroundResource(colorID);
                tvA1.setClickable(true);
                break;
            case 2:
                tvA2.setBackgroundResource(colorID);
                tvA2.setClickable(true);
                break;
            case 3:
                tvA3.setBackgroundResource(colorID);
                tvA3.setClickable(true);
                break;
            case 4:
                tvB1.setBackgroundResource(colorID);
                tvB1.setClickable(true);
                break;
            case 5:
                tvB2.setBackgroundResource(colorID);
                tvB2.setClickable(true);
                break;
            case 6:
                tvB3.setBackgroundResource(colorID);
                tvB3.setClickable(true);
                break;
            case 7:
                tvC1.setBackgroundResource(colorID);
                tvC1.setClickable(true);
                break;
            case 8:
                tvC2.setBackgroundResource(colorID);
                tvC2.setClickable(true);
                break;
            case 9:
                tvC3.setBackgroundResource(colorID);
                tvC3.setClickable(true);
                break;
            case 10:
                tvC4.setBackgroundResource(colorID);
                tvC4.setClickable(true);
                break;
            case 11:
                tvC5.setBackgroundResource(colorID);
                tvC5.setClickable(true);
                break;
            case 12:
                tvD1.setBackgroundResource(colorID);
                tvD1.setClickable(true);
                break;
            case 13:
                tvD2.setBackgroundResource(colorID);
                tvD2.setClickable(true);
                break;
            case 14:
                tvD3.setBackgroundResource(colorID);
                tvD3.setClickable(true);
                break;
            case 15:
                tvD4.setBackgroundResource(colorID);
                tvD4.setClickable(true);
                break;
            case 16:
                tvD5.setBackgroundResource(colorID);
                tvD5.setClickable(true);
                break;
            case 17:
                tvD6.setBackgroundResource(colorID);
                tvD6.setClickable(true);
                break;
            case 18:
                tvD7.setBackgroundResource(colorID);
                tvD7.setClickable(true);
                break;
            case 19:
                tvD8.setBackgroundResource(colorID);
                tvD8.setClickable(true);
                break;
            case 20:
                tvD9.setBackgroundResource(colorID);
                tvD9.setClickable(true);
                break;
        }
    }

    private void showLockDetails(int marketLockId) {
        loadLockInformation lockInformation = new loadLockInformation();
        lockInformation.execute(String.valueOf(marketLockId));
    }

    private class ReserveLock extends AsyncTask<String, Void, String> {
        public static final String URL = "https://jongtalad-web-api.herokuapp.com/merchants/";

        @Override
        protected String doInBackground(String... values) {
            String newUrl = URL + merchantId + "/markets/locks/" + values[0] + "/reserve";
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("productTypeId", values[1])
                    .add("price", String.valueOf(price))
                    .add("saleDate", timeDate)
                    .build();
            Request request = new Request.Builder()
                    .url(newUrl)
                    .post(requestBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
                // I want to dealt with status code 409 in onPostExecute so that's why this is commented
//                if (response.isSuccessful()) {
//                    return response.body().string();
//                } else {
//                    return "Not Success - code : " + response.code();
//                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error - " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            int resCode = 404;
            int marketLockId = 0;
            try {
                JSONObject obj = new JSONObject(s);
                resCode = obj.getInt("code");
                JSONObject res = obj.getJSONObject("response");
                marketLockId = res.getInt("reservedMarketLockId");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (resCode == 200) {
                setLockStatus(marketLockId, R.color.lockStatusOuccupied);
                LockReservedDialogFragment lockReserved = new LockReservedDialogFragment();
                lockReserved.show(getFragmentManager(), "lockReservedDialog");
//                FirebaseMessaging.getInstance().subscribeToTopic("lockReserved");
            } else if (resCode == 409) {
                LockMaximumReservedDialogFragment lockMaximumReserved = new LockMaximumReservedDialogFragment();
                lockMaximumReserved.show(getFragmentManager(), "lockMaximumReservedDialogFragment");
            } else {
                Toast.makeText(getActivity(), "Reserve failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class LoadDataToTv extends AsyncTask<Void, Void, String> {
        private static final String URL = "https://jongtalad-web-api.herokuapp.com/markets/";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setMarketLockClickable(false);
            loadingMarketDataDialog = ProgressDialog.show(getContext(), "Fetch market data", "Loading...", true, false);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String newUrl = URL + marketId + "/locks/types/" + 1 + "/?saleDate=" + timeDate;
            OkHttpClient okHttpClient = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(newUrl)
                    .get()
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
            int resCode = 404;
            int[] marketLockId = {0};

            try {
                JSONObject obj = new JSONObject(s);
                resCode = obj.getInt("code");
                JSONArray res = obj.getJSONArray("response");
                marketLockId = new int[res.length()];
                for (int i = 0; i < res.length(); i++) {
                    marketLockId[i] = res.getJSONObject(i).getInt("marketLockId");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (resCode == 200) {
                for (int i = 0; i < marketLockId.length; i++) {
                    setLockStatus(marketLockId[i], R.color.lockStatusOuccupied);
                }
            }
        }
    }

    private class RefreshMarketLock extends AsyncTask<Void, Void, String> {
        private static final String URL = "https://jongtalad-web-api.herokuapp.com/markets/";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingRefreshMarketLockDialog = ProgressDialog.show(getContext(), "Fetch market lock list", "Loading...", true, false);
            for (int i = 0; i < 20; i++) {
                setLockStatus(i, R.color.lockStatausNotOuccupied);
            }
            setMarketLockClickable(false);
            loadLockname loadLockname = new loadLockname();
            loadLockname.execute();

        }

        @Override
        protected String doInBackground(Void... voids) {
            String newUrl = URL + marketId + "/locks/types/" + 1 + "/?saleDate=" + timeDate;
            OkHttpClient okHttpClient = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(newUrl)
                    .get()
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
            int resCode = 404;
            int[] marketLockId = {0};

            try {
                JSONObject obj = new JSONObject(s);
                resCode = obj.getInt("code");
                JSONArray res = obj.getJSONArray("response");
                marketLockId = new int[res.length()];
                for (int i = 0; i < res.length(); i++) {
                    marketLockId[i] = res.getJSONObject(i).getInt("marketLockId");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            loadingRefreshMarketLockDialog.dismiss();
            if (resCode == 200) {
                for (int i = 0; i < marketLockId.length; i++) {
                    setLockStatus(marketLockId[i], R.color.lockStatusOuccupied);
                }
            }
        }
    }

    private class loadLockname extends AsyncTask<String, Void, String> {
        public static final String URL = "https://jongtalad-web-api.herokuapp.com/markets/";

        @Override
        protected String doInBackground(String... values) {
            String newUrl = URL + marketId + "/locks/types/" + 0 + "/?saleDate=" + timeDate;

            OkHttpClient okHttpClient = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(newUrl)
                    .get()
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
            int resCode;
            try {
                JSONObject obj = new JSONObject(s);
                resCode = obj.getInt("code");
                JSONArray res = obj.getJSONArray("response");
                marketLockId = new int[res.length()];
                marketLockName = new String[res.length()];
                for (int i = 0; i < res.length(); i++) {
                    marketLockId[i] = res.getJSONObject(i).getInt("marketLockId");
                    marketLockName[i] = res.getJSONObject(i).getString("marketLockName");
                }
            } catch (JSONException e) {
                resCode = 404;
                marketLockId = new int[1];
                marketLockName = new String[1];

                marketLockId[0] = 0;
                marketLockName[0] = "None";

                e.printStackTrace();
            }

            if (resCode != 200) {
                marketLockId = new int[1];
                marketLockName = new String[1];

                marketLockId[0] = 0;
                marketLockName[0] = "None";
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner_view, marketLockName);
            adapter.setDropDownViewResource(R.layout.custom_spinner_drop_down);
            spLock.setAdapter(adapter);
        }
    }

    private class loadProductType extends AsyncTask<Void, Void, String> {
        public static final String URL = "https://jongtalad-web-api.herokuapp.com/producttypes";

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(URL)
                    .get()
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
            int resCode;
            try {
                JSONObject obj = new JSONObject(s);
                resCode = obj.getInt("code");
                JSONArray res = obj.getJSONArray("response");
                productTypeId = new int[res.length()];
                productTypeName = new String[res.length()];
                for (int i = 0; i < res.length(); i++) {
                    productTypeId[i] = res.getJSONObject(i).getInt("productTypeId");
                    productTypeName[i] = res.getJSONObject(i).getString("productTypeName");
                }

            } catch (JSONException e) {
                resCode = 404;
                productTypeId = new int[1];
                productTypeName = new String[1];

                productTypeId[0] = 0;
                productTypeName[0] = "None";
                e.printStackTrace();
            }

            if (resCode != 200) {
                productTypeId = new int[1];
                productTypeName = new String[1];

                productTypeId[0] = 0;
                productTypeName[0] = "None";
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner_view, productTypeName);
            adapter.setDropDownViewResource(R.layout.custom_spinner_drop_down);
            spProductType.setAdapter(adapter);
            loadingMarketDataDialog.dismiss();
        }
    }

    private class loadLockInformation extends AsyncTask<String, Void, String> {
        public static final String URL = "https://jongtalad-web-api.herokuapp.com/markets/locks/";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingMarketDetailDialog = ProgressDialog.show(getContext(), "Fetch lock detail", "Loading...", true, false);
        }

        @Override
        protected String doInBackground(String... values) {
            String newUrl = URL + values[0] + "/detail/?saleDate=" + timeDate;
            OkHttpClient okHttpClient = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(newUrl)
                    .get()
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
            int resCode = 404;
            int[] resMerchantId = {0};
            String[] resMerchantFirstName = {"None"};
            String[] resMerchantLastname = {"None"};
            String merchantFullname = "";
            String[] productTypeName = {"None"};
            String[] marketLockName = {"None"};
            String showMerchantName = "";

            try {
                JSONObject obj = new JSONObject(s);
                resCode = obj.getInt("code");
                JSONArray res = obj.getJSONArray("response");
                resMerchantId = new int[res.length()];
                resMerchantFirstName = new String[res.length()];
                resMerchantLastname = new String[res.length()];
                productTypeName = new String[res.length()];
                marketLockName = new String[res.length()];
                for (int i = 0; i < res.length(); i++) {
                    if (res.getJSONObject(i).getString("merchantName") != "null") {
                        resMerchantFirstName[i] = res.getJSONObject(i).getString("merchantName");
                        resMerchantId[i] = res.getJSONObject(i).getInt("merchantId");
                        resMerchantLastname[i] = res.getJSONObject(i).getString("merchantSurname");
                        merchantFullname = resMerchantFirstName[i] + " " + resMerchantLastname[i];
                    }
                    productTypeName[i] = res.getJSONObject(i).getString("productTypeName");
                    marketLockName[i] = res.getJSONObject(i).getString("marketLockName");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (resMerchantId[0] == merchantId) {
                showMerchantName = "" + merchantFullname + "\n";
            } else {
                showMerchantName = "";
            }

            loadingMarketDetailDialog.dismiss();
            if (resCode == 200) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Market Lock Details")
                        .setMessage("" + marketLockName[0] + "\n" +
                                showMerchantName +
                                productTypeName[0])
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
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

    private void setMarketLockClickable(boolean clickable) {
        tvA1.setClickable(clickable);
        tvA2.setClickable(clickable);
        tvA3.setClickable(clickable);
        tvB1.setClickable(clickable);
        tvB2.setClickable(clickable);
        tvB3.setClickable(clickable);
        tvC1.setClickable(clickable);
        tvC2.setClickable(clickable);
        tvC3.setClickable(clickable);
        tvC4.setClickable(clickable);
        tvC5.setClickable(clickable);
        tvD1.setClickable(clickable);
        tvD2.setClickable(clickable);
        tvD3.setClickable(clickable);
        tvD4.setClickable(clickable);
        tvD5.setClickable(clickable);
        tvD6.setClickable(clickable);
        tvD7.setClickable(clickable);
        tvD8.setClickable(clickable);
        tvD9.setClickable(clickable);
    }

    private Date timeDate() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        return date;
    }

    // Function to find the index of an element in a primitive array in Java
    public static int find(String[] a, String target) {
        for (int i = 0; i < a.length; i++)
            if (a[i] == target)
                return i;

        return -1;
    }
}
