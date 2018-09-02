package com.example.teerasaksathu.customers.fragment.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.teerasaksathu.customers.R;

/**
 * Created by Naetirat on 11/15/2017.
 */

public class MarketRuleDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Market Rules");
//        "ตัดรอบการจอง 14:00 น."
        builder.setMessage("You can reserve more then 3 locks"+ "\n")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        return builder.create();
    }
}