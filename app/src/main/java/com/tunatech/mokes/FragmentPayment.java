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
public class FragmentPayment extends Fragment {

    private MainActivity mainActivity;
    public static FragmentPayment newInstance(){
        FragmentPayment f = new FragmentPayment();
        return f;
    }

    public FragmentPayment(){
        Log.d(LoginActivity.TAG, "construct fragment payment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment,container,false);

        Button btnTopUp = (Button) view.findViewById(R.id.paymmnt_btn_topup);
        Button btnNotNow = (Button) view.findViewById(R.id.paymmnt_btn_not_now);

        Button[] btn_ctrl = {btnTopUp, btnNotNow};

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
        mainActivity = (MainActivity) context;
    }

    private void onBtnCliked(View item){
        int id = item.getId();

        if (id == R.id.paymmnt_btn_topup) {
            Toast.makeText(getContext(), "top up clicked", Toast.LENGTH_SHORT).show();
            mainActivity.setupFragmentUser();
            mainActivity.setupFragmentTopUp();
        }
        else if (id == R.id.paymmnt_btn_not_now) {
            Toast.makeText(getContext(), "not now clicked", Toast.LENGTH_SHORT).show();
            mainActivity.onBackPressed();
        }
    }
}
