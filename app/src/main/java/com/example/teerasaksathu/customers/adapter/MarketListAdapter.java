package com.example.teerasaksathu.customers.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teerasaksathu.customers.R;
import com.squareup.picasso.Picasso;


/**
 * Created by teerasaksathu on 11/10/2017 AD.
 */

public class MarketListAdapter extends BaseAdapter {
    private Context ojdContext;
    private int[] marketId;
    private String[] marketName;
    private String[] marketAddress;
    private String[] pictureUrl;


    public MarketListAdapter(Context ojdContext, int[] marketId, String[] marketName, String[] marketAddress, String[] pictureUrl) {
        this.ojdContext = ojdContext;
        this.marketId = marketId;
        this.marketName = marketName;
        this.marketAddress = marketAddress;
        this.pictureUrl = pictureUrl;

    }


    @Override
    public int getCount() {
        return this.marketId.length;
    }

    @Override
    public String getItem(int i) {
        return this.marketName[i];
    }

    @Override
    public long getItemId(int i) {
        return this.marketId[i];
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) ojdContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ojdView = layoutInflater.inflate(R.layout.list_item_market, viewGroup, false);

        TextView tvNameMarket = ojdView.findViewById(R.id.tvNameMarket);
        tvNameMarket.setText(marketName[i]);

        TextView tvAddress = ojdView.findViewById(R.id.tvAddress);
        tvAddress.setText(marketAddress[i]);

        ImageView imageView = ojdView.findViewById(R.id.imageView);
        try {
            Picasso.with(viewGroup.getContext())
                    .load(pictureUrl[i])
//                    .placeholder(R.drawable.mock_market)
                    .into(imageView);
        } catch (IllegalArgumentException e) {
            Picasso.with(viewGroup.getContext())
                    .load(R.drawable.mock_market)
                    .into(imageView);

        }
        return ojdView;
    }
}
