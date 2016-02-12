package com.tunatech.mokes;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by royyan on 2/6/2016.
 */
public class FragmentUser extends android.support.v4.app.Fragment{

    private TextView tvUserName;
    private ImageView imgUserProfilePic;
    private TextView tvSaldoMain;
    private TextView tvlblYourBalance;

    public static FragmentUser newInstance(){
        FragmentUser f = new FragmentUser();
        return f;
    }

    public FragmentUser(){
        Log.d(LoginActivity.TAG, "construct fragment user");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);

        tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        imgUserProfilePic = (ImageView) view.findViewById(R.id.image_user);
        tvSaldoMain = (TextView) view.findViewById(R.id.tv_saldo_main);
        tvlblYourBalance = (TextView) view.findViewById(R.id.tv_lbl_your_saldo);
        return view;
    }

    public void SetUserName(String user){
        tvUserName.setText(user);
    }

    public void SetSaldo(String saldo){
        tvSaldoMain.setText(saldo);
    }

    public void SetProfilePictre(Drawable userpic){
        imgUserProfilePic.setImageDrawable(userpic);
    }

    public void SetMkiosTitle(Boolean state){
        if (state) {
            tvlblYourBalance.setText(R.string.str_your_mkios_saldo);
        }else{
            tvlblYourBalance.setText(R.string.str_your_saldo);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            ((MainActivity) context).SetupFragmentUserValue();
        }
        catch (Exception ex){
            Log.d(LoginActivity.TAG,"Error : cant Setup Fragment User Value, "+ex.getMessage());
        }
    }
}
