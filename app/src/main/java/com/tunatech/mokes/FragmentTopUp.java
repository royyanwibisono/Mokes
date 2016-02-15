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
public class FragmentTopUp extends Fragment{

    private MainActivity mainActivity;

    public static FragmentTopUp newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title",title);
        FragmentTopUp fragment = new FragmentTopUp();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentTopUp(){
        Log.d(LoginActivity.TAG, "construct fragment top up");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topup,container,false);

        Button btnConfirmPayment = (Button) view.findViewById(R.id.topup_confirm);

        btnConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnCliked(v);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)context;
    }

    private void onBtnCliked(View item){
        int id = item.getId();

        if (id == R.id.topup_confirm) {
            Toast.makeText(getContext(), "confirm clicked", Toast.LENGTH_SHORT).show();
            mainActivity.setupFragmentUser();
            mainActivity.setupFragmentConfirmation();
        }
    }
}
