package com.tunatech.mokes;

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
 * Created by royyan on 2/12/2016.
 */
public class FragmentConfirmation extends Fragment {
    public static FragmentConfirmation newInstance(){
        FragmentConfirmation f = new FragmentConfirmation();
        return f;
    }

    public FragmentConfirmation(){
        Log.d(LoginActivity.TAG, "construct fragment confirmation");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation,container,false);

        Button btnSend = (Button) view.findViewById(R.id.confirm_btn_upload);
        Button btnBrowse = (Button) view.findViewById(R.id.confirm_btn_select_file);

        Button[] btn_ctrl = {btnSend, btnBrowse};

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

        if (id == R.id.confirm_btn_upload) {
            Toast.makeText(getContext(), "upload clicked", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.confirm_btn_select_file) {
            Toast.makeText(getContext(), "select file clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
