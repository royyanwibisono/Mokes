package com.tunatech.mokes;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by royyan on 2/11/2016.
 */
public class FragmentPurchaseCredit extends Fragment {

    MainActivity mainActivity;

    public static FragmentPurchaseCredit newInstance(){
        FragmentPurchaseCredit f = new FragmentPurchaseCredit();
        return f;
    }

    public FragmentPurchaseCredit(){
        Log.d(LoginActivity.TAG, "construct Fragment Purchase Credit");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase_credit,container,false);

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)context;
    }

    private void onBtnCliked(View item){
        int id = item.getId();

        if (id == R.id.purch_cr_btn_purchase) {
            Toast.makeText(getContext(), "purchase credit clicked", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.purch_cr_btn_add_balance) {
            Toast.makeText(getContext(), "add balance clicked", Toast.LENGTH_SHORT).show();
            mainActivity.setupFragmentUser();
            mainActivity.setupFragmentTopUp();
        }
    }
}
