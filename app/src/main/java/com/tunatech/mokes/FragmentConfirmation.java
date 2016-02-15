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
 * Created by royyan on 2/12/2016.
 */
public class FragmentConfirmation extends Fragment {
    private EditText etUserTarget, etNominal, etMessage;
    private Spinner spSelectBank;

    private MainActivity mainActivity;

    public static FragmentConfirmation newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title",title);
        FragmentConfirmation fragment = new FragmentConfirmation();
        fragment.setArguments(args);
        return fragment;
    }


    public FragmentConfirmation(){
        Log.d(LoginActivity.TAG, "construct fragment confirmation");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation,container,false);

        spSelectBank = (Spinner)view.findViewById(R.id.confirm_select_bank);
        etUserTarget = (EditText) view.findViewById(R.id.confirm_user_account_target);
        etNominal = (EditText) view.findViewById(R.id.confirm_nominal);
        etMessage = (EditText)view.findViewById(R.id.confirm_write_msg);

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)context;
    }

    private void attemptUpload() {

        View[] etarry = {spSelectBank,etUserTarget,etNominal, etMessage};
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
                if (TextUtils.isEmpty(input_data) && item != etMessage) {
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

            mainActivity.cmdUploadConfirmation(MainActivity.RESULT_OK, args);
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

        if (id == R.id.confirm_btn_upload) {
            attemptUpload();
        }
        else if (id == R.id.confirm_btn_select_file) {
            Toast.makeText(getContext(), "select file clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
