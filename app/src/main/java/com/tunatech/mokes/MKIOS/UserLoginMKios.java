package com.tunatech.mokes.MKIOS;

import android.os.AsyncTask;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserLoginMKios extends AsyncTask<Void, Void, Boolean> {

    private boolean isLogedIn;
    private boolean isUserUnKnown;
    public interface IUserLoginMKios {
        void OnPostExecute(Boolean success);
        void OnCancelled();
    }
    private final String mPhone;
    private final String mPassword;
    private IUserLoginMKios AuthTask;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "123456789:000000", "7778899:111111"
    };

    public UserLoginMKios(IUserLoginMKios authTask, String phone, String pin) {
        AuthTask = (IUserLoginMKios)authTask;
        mPhone = phone;
        mPassword = pin;
        isLogedIn = false;
    }
    public Boolean IsLoggedIn(){
        return isLogedIn;
    }
    public Boolean IsUserUnKnown(){
        return isUserUnKnown;
    }
    @Override
    protected Boolean doInBackground(Void... params) {
        isLogedIn = false;
        // TODO: attempt authentication against a network service.

        try {
            // Simulate network access.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return false;
        }

        int count = 0;
        for (String credential : DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(mPhone)) {
                isUserUnKnown = false;

                // Account exists, return true if the password matches.
                isLogedIn = pieces[1].equals(mPassword);
                return isLogedIn;
            }
            count++;
        }

        if (count > 0 && AuthTask != null){
            isUserUnKnown = true;

            return false;
        }

        // TODO: register the new account here.
        return true;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (AuthTask != null){
            AuthTask.OnPostExecute(success);
        }
    }

    @Override
    protected void onCancelled() {
        if (AuthTask != null){
            AuthTask.OnCancelled();
        }
    }
}

