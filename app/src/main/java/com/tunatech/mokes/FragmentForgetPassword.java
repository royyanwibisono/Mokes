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

/**
 * Created by royyan on 2/10/2016.
 */
public class FragmentForgetPassword extends Fragment {
    private EditText etEmail;
    private FragmentRegister.IFragRegister context;

    public static FragmentForgetPassword newInstance(){
        FragmentForgetPassword f = new FragmentForgetPassword();
        return f;
    }

    public FragmentForgetPassword(){
        Log.d(LoginActivity.TAG, "construct fragment forget password");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LoginActivity.TAG, "onCreateView fragment forget password");
        View view = inflater.inflate(R.layout.login_frag_forget_password,container,false);
        view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

        etEmail = (EditText)view.findViewById(R.id.forget_email);

        Button btnRegister = (Button) view.findViewById(R.id.forget_send_button);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptForgetPassword();
            }
        });

        View backToLogin = (View) view.findViewById(R.id.forget_back_to_login);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.FinishRegister(LoginCantActivity.RESULT_CANCELED,null);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (FragmentRegister.IFragRegister) context;
    }

    private void attemptForgetPassword() {

        etEmail.setError(null);

        boolean cancel = false;

        String input_data = etEmail.getText().toString();
        if (TextUtils.isEmpty(input_data)){
            cancel = true;
            etEmail.setError(getString(R.string.error_field_required));
        }
        else if(!isEmailValid(input_data)){
            etEmail.setError(getString(R.string.error_invalid_email));
            cancel = true;
        }


        if (cancel) {
            etEmail.requestFocus();
        } else {
            Intent goingBack = new Intent();
            goingBack.putExtra(String.valueOf(etEmail.getId()),etEmail.getText().toString());
            context.FinishRegister(LoginCantActivity.RESULT_OK, goingBack);

        }
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

}
