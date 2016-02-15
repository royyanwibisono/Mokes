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
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by royyan on 2/6/2016.
 */
public class FragmentControlRoom extends Fragment {

    private MainActivity mainActivity;

    public static FragmentControlRoom newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title",title);
        FragmentControlRoom fragment = new FragmentControlRoom();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentControlRoom(){
        Log.d(LoginActivity.TAG, "construct fragment control room");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_room,container,false);

        ImageButton btnMyPayment = (ImageButton) view.findViewById(R.id.btn_payment);
        ImageButton btnTransfer = (ImageButton) view.findViewById(R.id.btn_transfer);
        ImageButton btnPurchaseCredit = (ImageButton) view.findViewById(R.id.btn_purchase_credit);
        ImageButton btnAddBalance = (ImageButton) view.findViewById(R.id.btn_add_balance);

        ImageButton[] btn_ctrl = {btnMyPayment, btnTransfer, btnPurchaseCredit, btnAddBalance};

        for (ImageButton element: btn_ctrl) {
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

        if (id == R.id.btn_payment) {
            Toast.makeText(getContext(),"payment clicked", Toast.LENGTH_SHORT).show();
            mainActivity.setupFragmentUser();
            mainActivity.setupFragmentPayment();
        }
        else if (id == R.id.btn_transfer) {
            Toast.makeText(getContext(),"tranfer clicked", Toast.LENGTH_SHORT).show();
            mainActivity.setupFragmentUser();
            mainActivity.setupFragmentTransfer();
        }
        else if (id == R.id.btn_purchase_credit) {
            Toast.makeText(getContext(),"purchase clicked", Toast.LENGTH_SHORT).show();
            mainActivity.setupFragmentUser();
            mainActivity.setupFragmentPurchaseCredit();
        }
        else if (id == R.id.btn_add_balance) {
            Toast.makeText(getContext(),"add balance clicked", Toast.LENGTH_SHORT).show();
            mainActivity.setupFragmentUser();
            mainActivity.setupFragmentTopUp();
        }
    }
}
