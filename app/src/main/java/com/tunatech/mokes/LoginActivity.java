package com.tunatech.mokes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tunatech.mokes.Domain.Authentication;
import com.tunatech.mokes.Domain.UserLoginTask;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements UserLoginTask.IUserLoginTask {

    public static final String TAG = "Tuna";
    private final int REG = 1;
    private final int FORGET_PSSWRD = 2;

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Boolean isUserUnknown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mEmailView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {

                if (id == EditorInfo.IME_ACTION_NEXT){
                    if (mPasswordView != null){
                        mPasswordView.requestFocus();
                    }
                    return true;
                }
                return false;
            }
        });

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button mEmailRegisterButton = (Button) findViewById(R.id.form_register_button);
        mEmailRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        TextView clickHere = (TextView) findViewById(R.id.login_click_here);
        clickHere.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptForgetPassword();
            }
        });

        ImageButton facbookBtn = (ImageButton) findViewById(R.id.btn_facebook);
        facbookBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               facebookAuth();
            }
        });

        ImageButton googleBtn = (ImageButton) findViewById(R.id.btn_google);
        googleBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                googleAuth();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        isUserUnknown = true;
        Log.d(TAG, "login activity onCreate done");
    }


    private void facebookAuth(){
        Toast.makeText(this,"Facebook clicked",Toast.LENGTH_SHORT).show();
    }
    private void googleAuth(){
        Toast.makeText(this,"Google clicked",Toast.LENGTH_SHORT).show();
    }
    private void attemptRegister() {
        Intent regIntent = new Intent(this,LoginCantActivity.class);
        String mode = "register";

        regIntent.putExtra("mode", mode);

        this.startActivityForResult(regIntent, REG);

    }
    private void attemptForgetPassword() {
        Intent regIntent = new Intent(this,LoginCantActivity.class);
        String mode = "forget";

        regIntent.putExtra("mode", mode);

        this.startActivityForResult(regIntent, FORGET_PSSWRD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null) return;

        if (requestCode == REG){
            String fName = data.getStringExtra(String.valueOf(R.id.form_reg_full_name));
            String username = data.getStringExtra(String.valueOf(R.id.form_reg_user_name));
            String phoneNo = data.getStringExtra(String.valueOf(R.id.form_reg_phone_number));
            String emailReg = data.getStringExtra(String.valueOf(R.id.form_reg_email));
            String passwReg = data.getStringExtra(String.valueOf(R.id.form_reg_password));

            Toast.makeText(this,fName+","+username+","+phoneNo+","+emailReg+","+passwReg,Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(this, email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void OnPostExecute(Boolean success) {
        mAuthTask = null;

        if (success) {
            Intent loggedIn = new Intent(this,MainActivity.class);
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();

            Authentication data = new Authentication("Tuna Tech");
            data.set_email(email);
            data.set_saldo("Rp 20.000,-");

            loggedIn.putExtra("auth", data);

            this.startActivity(loggedIn);

            Toast.makeText(this.getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
            showProgress(false);
            finish();
        } else if (isUserUnknown) {
            showProgress(false);
            mEmailView.setError(getString(R.string.invalid_user_name));
            mEmailView.requestFocus();
        } else {
            showProgress(false);
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            mPasswordView.requestFocus();
        }
    }

    @Override
    public void OnCancelled() {
        mAuthTask = null;
        showProgress(false);
    }

    @Override
    public void OnUserUnkown(Boolean state) {
        isUserUnknown = state;
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
    @Override
    public void onBackPressed() {

        System.exit(0);

    }
}

