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
import android.widget.Toast;

/**
 * Created by royyan on 2/11/2016.
 */
public class FragmentTransfer extends Fragment {
    private EditText etEmail,etNominal,etMessage;
    private MainActivity mainActivity;

    public static FragmentTransfer newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title",title);
        FragmentTransfer fragment = new FragmentTransfer();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentTransfer(){
        Log.d(LoginActivity.TAG, "construct fragment transfer");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transfer,container,false);

        etEmail = (EditText) view.findViewById(R.id.transfer_email);
        etNominal = (EditText) view.findViewById(R.id.transfer_nominal);
        etMessage = (EditText)view.findViewById(R.id.transfer_write_msg);

        Button btnAddBalance = (Button) view.findViewById(R.id.transfer_btn_add_balance);
        Button btnSend = (Button) view.findViewById(R.id.transfer_btn_send);

        Button[] btn_ctrl = {btnAddBalance, btnSend};

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

    private void attemptTransfer() {

        EditText[] etarry = {etEmail,etNominal,etMessage};
        // Reset errors.
        for(EditText item : etarry) {
            item.setError(null);
        }

        boolean cancel = false;
        View focusView = null;
        for(EditText item : etarry) {
            String input_data = item.getText().toString();
            if (TextUtils.isEmpty(input_data) && item != etMessage){
                cancel = true;
                item.setError(getString(R.string.error_field_required));
                focusView = item;
                break;
            }

            if(item == etEmail && !isValidEmail(input_data)){
                item.setError(getString(R.string.error_invalid_email));
                focusView = item;
                cancel = true;
                break;
            }

            if(item == etNominal && !isValidNominal(input_data)){
                item.setError(getString(R.string.error_invalid_input));
                focusView = item;
                cancel = true;
                break;
            }
        }


        if (cancel) {
            focusView.requestFocus();
        } else {

            Intent args = new Intent();
            for(EditText item : etarry) {
                args.putExtra(String.valueOf(item.getId()), item.getText().toString());
            }

            mainActivity.cmdTransferCredit(MainActivity.RESULT_OK, args);
        }
    }

    private boolean isValidEmail(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
    private boolean isValidNominal(String nominal) {
        //TODO: Replace this with your own logic
        try {
            long val = Long.parseLong(nominal);
            if (val > 0){
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception ex) {
            return false;
        }
    }

    private void onBtnCliked(View item){
        int id = item.getId();

        if (id == R.id.transfer_btn_send) {
            Toast.makeText(getContext(), "send clicked", Toast.LENGTH_SHORT).show();
            attemptTransfer();
        }
        else if (id == R.id.transfer_btn_add_balance) {
            Toast.makeText(getContext(), "add balance clicked", Toast.LENGTH_SHORT).show();
            mainActivity.setupFragmentUser();
            mainActivity.setupFragmentTopUp();
        }
    }
}
