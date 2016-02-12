package com.tunatech.mokes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tunatech.mokes.Domain.Authentication;
import com.tunatech.mokes.MKIOS.FragmentLogin;
import com.tunatech.mokes.MKIOS.FragmentMKiosDashBoard;
import com.tunatech.mokes.MKIOS.UserLoginMKios;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,FragmentLogin.IMKiosLogin,UserLoginMKios.IUserLoginMKios {

    private Authentication UserBridge;
    private FragmentUser fragmentUser;
    //private FragmentControlRoom controlRoom;
    private Fragment Misc;
    private UserLoginMKios MKiosAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Intent loggedIn = getIntent();
        if(loggedIn == null) {
            UserBridge = null;
            Toast.makeText(this.getApplicationContext(), "Authemtication error!",Toast.LENGTH_SHORT).show();
        } else {
            UserBridge = (Authentication) loggedIn.getSerializableExtra("auth");

            //Home Page
            setupFragmentUser();
            setupFragmentControlRoom();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(Misc != null && (Misc instanceof FragmentLogin)){
            MKiosLogin(RESULT_CANCELED, null);
        }
        else if(Misc != null && !(Misc instanceof FragmentControlRoom)){
            setupFragmentUser();
            setupFragmentControlRoom();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main_home) {
            setupFragmentUser();
            setupFragmentControlRoom();
        }
        else if (id == R.id.nav_main_add_balance) {
            setupFragmentUser();
            setupFragmentTopUp();
        }
        else if (id == R.id.nav_main_transfer) {
            setupFragmentUser();
            setupFragmentTransfer();
        }
        else if (id == R.id.nav_main_payment) {
            setupFragmentUser();
            setupFragmentPayment();
        }
        else if (id == R.id.nav_account_edit) {
            Toast.makeText(this, getString(R.string.drawer_menu_account_edit),Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_account_transaction_history) {
            Toast.makeText(this, getString(R.string.drawer_menu_account_transaction_history),Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_account_cash_reward) {
            Toast.makeText(this, getString(R.string.drawer_menu_account_cash_reward),Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_account_promo) {
            Toast.makeText(this, getString(R.string.drawer_menu_account_promo),Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_account_merchants) {
            Toast.makeText(this, getString(R.string.drawer_menu_account_merchants),Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_account_mkios) {
           if (MKiosAuth != null && MKiosAuth.IsLoggedIn()){
               setupFragmentUser();
               setupFragmentMKios();
           }
            else{

               Log.d(LoginActivity.TAG, "M-Kios attempt login...");
               setupFragmentMKiosLogin();
           }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupFragMainAfterFiltering(Fragment fragToShow){
        Log.d(LoginActivity.TAG, "Attaching Fragment...");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_item_detail_container, fragToShow)
                .commit();
        Log.d(LoginActivity.TAG, "Attached Fragment");
    }

    public void cmdTransferCredit(int result, Intent data){
        if (result == RESULT_OK) {
            String emailTarget = data.getStringExtra(String.valueOf(R.id.transfer_email));
            String nominal = data.getStringExtra(String.valueOf(R.id.transfer_nominal));
            String msg = data.getStringExtra(String.valueOf(R.id.transfer_write_msg));

            Toast.makeText(this, emailTarget + "," + nominal + ", " + msg + ", Loading...", Toast.LENGTH_LONG).show();
        }
    }
    public void cmdPuchaseCredit(int result, Intent data){
        if (result == RESULT_OK) {
            String emailTarget = data.getStringExtra(String.valueOf(R.id.purch_cr_phone));
            String nominal = data.getStringExtra(String.valueOf(R.id.transfer_nominal));
            String msg = data.getStringExtra(String.valueOf(R.id.transfer_write_msg));

            Toast.makeText(this, emailTarget + "," + nominal + ", " + msg + ", Loading...", Toast.LENGTH_LONG).show();
        }
    }

    private void setupFragmentMKiosLogin() {
        View fu = (View) findViewById(R.id.user_item_detail_container);
        fu.setVisibility(View.GONE);
        if (Misc == null || !(Misc instanceof FragmentLogin)) {
            Misc = FragmentLogin.newInstance();
        }
        setupFragMainAfterFiltering(Misc);
    }

    public void setupFragmentMKios() {
        if (MKiosAuth != null && MKiosAuth.IsLoggedIn()) {
            if (Misc == null || !(Misc instanceof FragmentMKiosDashBoard)) {
                Misc = FragmentMKiosDashBoard.newInstance();
            }
            setupFragMainAfterFiltering(Misc);
        }
    }

    public void setupFragmentConfirmation() {
        if (Misc == null || !(Misc instanceof FragmentConfirmation)) {
            Misc = FragmentConfirmation.newInstance();
        }
        setupFragMainAfterFiltering(Misc);
    }
    public void setupFragmentPayment() {
        if (Misc == null || !(Misc instanceof FragmentPayment)) {
            Misc = FragmentPayment.newInstance();
        }
        setupFragMainAfterFiltering(Misc);
    }

    public void setupFragmentPurchaseCredit() {
        if (Misc == null || !(Misc instanceof FragmentPurchaseCredit)) {
            Misc = FragmentPurchaseCredit.newInstance();
        }
        setupFragMainAfterFiltering(Misc);
    }

    public void setupFragmentTransfer() {
        if (Misc == null || !(Misc instanceof FragmentTransfer)) {
            Misc = FragmentTransfer.newInstance();
        }
        setupFragMainAfterFiltering(Misc);
    }
    public void setupFragmentControlRoom() {
        if (Misc == null || !(Misc instanceof FragmentControlRoom)) {
            Misc = FragmentControlRoom.newInstance();
        }
        setupFragMainAfterFiltering(Misc);
    }

    public void setupFragmentTopUp(){
        if (Misc == null || !(Misc instanceof FragmentTopUp)) {
            Misc = FragmentTopUp.newInstance();
        }
        setupFragMainAfterFiltering(Misc);
    }


    public void setupFragmentUser(){
        if(fragmentUser == null) {
            fragmentUser = FragmentUser.newInstance();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.user_item_detail_container, fragmentUser)
                .commit();
    }

    public void SetupFragmentUserValue(){
        if(fragmentUser != null && UserBridge!= null) {
            fragmentUser.SetUserName(UserBridge.get_username());
            fragmentUser.SetSaldo(UserBridge.get_saldo());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LoginActivity.TAG, "mokes activity onStart");
        // The activity is about to become visible.
    }
    @Override
    protected void onResume() {
        // The activity has become visible (it is now "resumed").
        super.onResume();
        Log.d(LoginActivity.TAG, "mokes activity onResume");

        SetupFragmentUserValue();

    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LoginActivity.TAG, "mokes activity onPause");
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LoginActivity.TAG, "mokes activity onStop");
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LoginActivity.TAG, "mokes activity onDestroy");
        // The activity is about to be destroyed.
    }

    @Override
    public void MKiosLogin(int result, Intent data) {
        if (result == RESULT_CANCELED){
            View fu = (View) findViewById(R.id.user_item_detail_container);
            fu.setVisibility(View.VISIBLE);

            setupFragmentUser();
            setupFragmentControlRoom();
            Toast.makeText(this,"M-Kios : Not Logged In",Toast.LENGTH_SHORT).show();
        }
        else if (result == RESULT_OK){
            if (data != null){

                String phoneNo = data.getStringExtra(String.valueOf(R.id.mkios_phone_no));
                String passwReg = data.getStringExtra(String.valueOf(R.id.mkios_pin));

                Toast.makeText(this,phoneNo+","+passwReg+", Loading...",Toast.LENGTH_LONG).show();
                MKiosAuth = new UserLoginMKios(this,phoneNo,passwReg);
                MKiosAuth.execute((Void) null);
            }
        }
    }

    @Override
    public void OnCancelled() {
        MKiosAuth = null;
        MKiosLogin(RESULT_CANCELED, null);
    }

    @Override
    public void OnPostExecute(Boolean success) {

        if (success) {
            View fu = (View) findViewById(R.id.user_item_detail_container);
            fu.setVisibility(View.VISIBLE);

            setupFragmentUser();
            setupFragmentMKios();

        } else if (MKiosAuth.IsUserUnKnown()) {

            if (Misc instanceof FragmentLogin) {
                ((FragmentLogin)Misc).showProgress(false);
                ((FragmentLogin)Misc).setErrorUserPhone(getString(R.string.invalid_user_name));
            }
            MKiosAuth = null;
        } else {

            if (Misc instanceof FragmentLogin) {
                ((FragmentLogin)Misc).showProgress(false);
                ((FragmentLogin)Misc).setErrorUserPin(getString(R.string.error_incorrect_password));
            }
            MKiosAuth = null;
        }
    }
}
