package com.tunatech.mokes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by royyan on 2/11/2016.
 */
public class FragmentPurchaseCredit extends Fragment {

    private EditText etPhoneNo;
    private Spinner spSelectProvider,spSelectNominal;
    private MainActivity mainActivity;

    public static FragmentPurchaseCredit newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title",title);
        FragmentPurchaseCredit fragment = new FragmentPurchaseCredit();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentPurchaseCredit(){
        Log.d(LoginActivity.TAG, "construct Fragment Purchase Credit");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase_credit,container,false);

        spSelectProvider = (Spinner)view.findViewById(R.id.purch_cr_select_provider);
        spSelectNominal = (Spinner)view.findViewById(R.id.purch_cr_select_nominal);
        etPhoneNo = (EditText) view.findViewById(R.id.purch_cr_phone);

        Button btnPurchase = (Button) view.findViewById(R.id.purch_cr_btn_purchase);
        Button btnAddBalance = (Button) view.findViewById(R.id.purch_cr_btn_add_balance);

        Button[] btn_ctrl = {btnPurchase, btnAddBalance};

        for (Button element: btn_ctrl) {
            element.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBtnCliked(v);
                }
            });
        }

        return view;
    }

    private void attemptPurchase() {

        View[] etarry = {etPhoneNo,spSelectProvider,spSelectNominal};
        // Reset errors.
        for(View item : etarry) {
            if (item instanceof EditText) {
                ((EditText)item).setError(null);
            }
            else {
                Spinner sp = (Spinner)item;
                TextView errorTxt = (TextView)sp.getSelectedView();
                errorTxt.setError(null);
            }
        }

        boolean cancel = false;
        View focusView = null;
        for(View v : etarry) {
            if (v instanceof EditText) {
                EditText item = (EditText) v;
                String input_data = item.getText().toString();
                if (TextUtils.isEmpty(input_data)) {
                    cancel = true;
                    item.setError(getString(R.string.error_field_required));
                    focusView = v;
                    break;
                }
            }
            else{
                Spinner item = (Spinner)v;
                TextView selItem = (TextView)item.getSelectedView();
                if (selItem.getText().toString().contains("--")) {
                    cancel = true;
                    selItem.setError(getString(R.string.error_item_spinner_required));
                    focusView = v;
                    break;
                }
            }
        }


        if (cancel) {
            focusView.requestFocus();
        } else {

            Intent args = new Intent();

            for(View v : etarry) {
                if (v instanceof EditText) {
                    EditText item = (EditText) v;
                    args.putExtra(String.valueOf(item.getId()), item.getText().toString());
                }
                else{
                    Spinner item = (Spinner)v;
                    args.putExtra(String.valueOf(item.getId()), item.getSelectedItem().toString());
                }
            }

            mainActivity.cmdPurchaseCredit(MainActivity.RESULT_OK, args);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)context;
    }

    private void onBtnCliked(View item){
        int id = item.getId();

        if (id == R.id.purch_cr_btn_purchase) {
            attemptPurchase();
        }
        else if (id == R.id.purch_cr_btn_add_balance) {
            mainActivity.setupFragmentUser();
            mainActivity.setupFragmentTopUp();
        }
    }
}
