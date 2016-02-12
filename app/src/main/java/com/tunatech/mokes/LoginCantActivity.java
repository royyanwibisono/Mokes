package com.tunatech.mokes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class LoginCantActivity extends AppCompatActivity implements FragmentRegister.IFragRegister{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_can_not_activity);


        Log.d(LoginActivity.TAG, "onCreate LoginCantActivity");
        Intent loader = getIntent();
        String mode = loader.getExtras().getString("mode").toString();
        Log.d(LoginActivity.TAG,"mode: "+mode);
        if (mode.contains("register")) {
            Log.d(LoginActivity.TAG, "FragmentRegister");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, FragmentRegister.newInstance())
                    .commit();
        }
        else if (mode.contains("forget")) {
            Log.d(LoginActivity.TAG, "FragmentForgetPassword");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, FragmentForgetPassword.newInstance())
                    .commit();
        }
        else{
            Log.d(LoginActivity.TAG, "nothing to show!");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        FinishRegister(RESULT_CANCELED, null);
    }

    @Override
    public void FinishRegister(int result, Intent data){
        setResult(result,data);
        finish();
    }
}
