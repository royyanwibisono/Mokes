package com.tunatech.mokes.MKIOS;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import android.widget.ProgressBar;

import com.tunatech.mokes.LoginActivity;
import com.tunatech.mokes.MainActivity;
import com.tunatech.mokes.R;

/**
 * Created by royyan on 2/11/2016.
 */
public class FragmentLogin extends Fragment {

    private EditText etPhoneNo,etPass;
    private IMKiosLogin context;
    private View progressBar, mLoginFormView;

    public interface IMKiosLogin{
        void MKiosLogin(int result, Intent data);
    }

    public static FragmentLogin newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title",title);
        FragmentLogin fragment = new FragmentLogin();
        fragment.setArguments(args);
        return fragment;
    }
    public FragmentLogin(){
        Log.d(LoginActivity.TAG, "construct fragment M-Kios Login");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mkios_frag_login,container,false);

        Log.d(LoginActivity.TAG,"M-Kios Fragment Login onCreateView");

        etPhoneNo = (EditText)view.findViewById(R.id.mkios_phone_no);
        etPass = (EditText)view.findViewById(R.id.mkios_pin);

        progressBar = (View) view.findViewById(R.id.mkios_login_progress);
        mLoginFormView = (View)  view.findViewById(R.id.mkios_login_form);

        Button btnSignIn = (Button) view.findViewById(R.id.mkios_btn_sign_in);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (IMKiosLogin) context;
    }

    private void attemptLogin() {

        EditText[] etarry = {etPhoneNo,etPass};
        // Reset errors.
        for(EditText item : etarry) {
            item.setError(null);
        }

        boolean cancel = false;
        View focusView = null;
        for(EditText item : etarry) {
            String input_data = item.getText().toString();
            if (TextUtils.isEmpty(input_data)){
                cancel = true;
                item.setError(getString(R.string.error_field_required));
                focusView = item;
                break;
            }

            if(item == etPass && !isPasswordValid(input_data)){
                item.setError(getString(R.string.error_invalid_password));
                focusView = item;
                cancel = true;
                break;
            }
        }


        if (cancel) {
            focusView.requestFocus();
        } else {

            Intent goingBack = new Intent();
            for(EditText item : etarry) {
                goingBack.putExtra(String.valueOf(item.getId()),item.getText().toString());
            }
            showProgress(true);
            context.MKiosLogin(MainActivity.RESULT_OK, goingBack);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }

    public void setErrorUserPhone(String msg){

        etPhoneNo.setError(msg);
        etPhoneNo.requestFocus();
    }
    public void setErrorUserPin(String msg){

        etPass.setError(msg);
        etPass.requestFocus();
    }
}
