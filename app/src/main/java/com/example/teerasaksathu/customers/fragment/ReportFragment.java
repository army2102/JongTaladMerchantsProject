package com.example.teerasaksathu.customers.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.teerasaksathu.customers.R;
import com.example.teerasaksathu.customers.activity.MainActivity;
import com.example.teerasaksathu.customers.adapter.ReportListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ReportFragment extends Fragment {

    private ListView report;

    private String[] nameMarket;
    private String[] lockName;
    private String[] date;
    private String[] pictureUrl;
    private String username;

    public ReportFragment() {
        super();
    }

    public static ReportFragment newInstance() {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
//        username = MainActivity.intentUsername.getStringExtra("username");
        report = rootView.findViewById(R.id.lvReservedHistoryList);
        load_Reprot_data load_reprot_data = new load_Reprot_data();
        load_reprot_data.execute(username);
        // Init 'View' instance(s) with rootView.findViewById here
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

    private class load_Reprot_data extends AsyncTask<String, Void, String> {

        public static final String URL = "http://www.jongtalad.com/doc/load_lock_reservation_history.php";


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
            Log.d("REPORT_DATA", s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                nameMarket = new String[jsonArray.length()];
                lockName = new String[jsonArray.length()];
                date = new String[jsonArray.length()];
                pictureUrl = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    nameMarket[i] = jsonObject.getString("market_name");
                    lockName[i] = jsonObject.getString("name");
                    date[i] = jsonObject.getString("sale_date");
                    pictureUrl[i] = jsonObject.getString("picture_url");

                    Log.d("ReportPage", "onPostExecute: " + nameMarket[i]);
                }
                ReportListAdapter reportListAdapter = new ReportListAdapter(getActivity(), nameMarket, date, lockName, pictureUrl);
                report.setAdapter(reportListAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

}
