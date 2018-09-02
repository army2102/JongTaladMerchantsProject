package com.example.teerasaksathu.customers.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.teerasaksathu.customers.R;
import com.example.teerasaksathu.customers.activity.LockReservationActivity;
import com.example.teerasaksathu.customers.activity.MainActivity;
import com.example.teerasaksathu.customers.adapter.MarketListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MarketListFragment extends Fragment {
    ListView marketList;
    int merchantId;
    private ProgressDialog loadingDialog;

    public MarketListFragment() {
        super();
    }

    public static MarketListFragment newInstance() {
        MarketListFragment fragment = new MarketListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_market_list, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        loadingDialog = ProgressDialog.show(getContext(), "Fetch market list", "Loading...", true, false);

        loadMarketList loadMarketList = new loadMarketList();
        loadMarketList.execute();


        merchantId = MainActivity.intent.getIntExtra("merchantId", 0);

        marketList = rootView.findViewById(R.id.marketList);
        marketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), LockReservationActivity.class);
                intent.putExtra("merchantId", merchantId);
                intent.putExtra("marketId", (int) adapterView.getItemIdAtPosition(i));
                intent.putExtra("marketName", "" + adapterView.getItemAtPosition(i));
                startActivity(intent);
            }
        });


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


    private class loadMarketList extends AsyncTask<Void, Void, String> {
        public static final String URL = "https://jongtalad-web-api.herokuapp.com/merchants/markets";

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
            int[] marketId;
            String[] marketName;
            String[] marketAddress;
            String[] pictureUrl;

            try {
                JSONObject obj = new JSONObject(s);
                JSONArray res = obj.getJSONArray("response");
                marketId = new int[res.length()];
                marketName = new String[res.length()];
                marketAddress = new String[res.length()];
                pictureUrl = new String[res.length()];
                for (int i = 0; i < res.length(); i++) {
                    marketId[i] = res.getJSONObject(i).getInt("marketId");
                    marketName[i] = res.getJSONObject(i).getString("marketName");
                    marketAddress[i] = res.getJSONObject(i).getString("marketAddress");
                    pictureUrl[i] = res.getJSONObject(i).getString("pictureUrl");
                }
            } catch (JSONException e) {
                e.printStackTrace();

                marketId = new int[1];
                marketName = new String[1];
                marketAddress = new String[1];
                pictureUrl = new String[1];

                marketId[0] = 0;
                marketName[0] = "None";
                marketAddress[0] = "None";
                pictureUrl[0] = "None";
            }
            MarketListAdapter marketListAdapter = new MarketListAdapter(getActivity(), marketId, marketName, marketAddress, pictureUrl);
            marketList.setAdapter(marketListAdapter);
            loadingDialog.dismiss();
        }
    }
}
