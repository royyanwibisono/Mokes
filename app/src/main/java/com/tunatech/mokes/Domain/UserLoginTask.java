package com.tunatech.mokes.Domain;

import android.os.AsyncTask;

import java.io.Serializable;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserLoginTask extends AsyncTask<Void, Void, Boolean> implements Serializable {

    private boolean isLogedIn;
    public interface IUserLoginTask {
        void OnPostExecute(Boolean success);
        void OnCancelled();
        void OnUserUnkown(Boolean state);
    }
    private final String mEmail;
    private final String mPassword;
    private IUserLoginTask AuthTask;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    public UserLoginTask(IUserLoginTask authTask, String email, String password) {
        AuthTask = (IUserLoginTask)authTask;
        mEmail = email;
        mPassword = password;
    }
    public Boolean IsLoggedIn(){
        return isLogedIn;
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
            if (pieces[0].equals(mEmail)) {
                AuthTask.OnUserUnkown(false);
                // Account exists, return true if the password matches.
                isLogedIn = pieces[1].equals(mPassword);
                return isLogedIn;
            }
            count++;
        }

        if (count > 0 && AuthTask != null){
            AuthTask.OnUserUnkown(true);
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

