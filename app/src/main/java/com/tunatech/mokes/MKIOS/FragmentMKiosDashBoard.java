package com.tunatech.mokes.MKIOS;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tunatech.mokes.LoginActivity;
import com.tunatech.mokes.R;

/**
 * Created by royyan on 2/11/2016.
 */
public class FragmentMKiosDashBoard extends Fragment {
    public static FragmentMKiosDashBoard newInstance(){
        FragmentMKiosDashBoard f = new FragmentMKiosDashBoard();
        return f;
    }

    public FragmentMKiosDashBoard(){
        Log.d(LoginActivity.TAG, "construct fragment M-Kios");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mkios_frag_dashboard,container,false);

        Button btnAddBalance = (Button) view.findViewById(R.id.mkios_btn_add_balance);

        Button[] btn_ctrl = {btnAddBalance};

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

    private void onBtnCliked(View item){
        int id = item.getId();

        if (id == R.id.mkios_btn_add_balance) {
            Toast.makeText(getContext(), "add balance clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
