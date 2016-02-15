package com.tunatech.mokes;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by royyan on 2/13/2016.
 */
public class DialogTuna  extends Dialog implements android.view.View.OnClickListener {

    private Activity c;
    //        private Dialog d;
    private View ok;
    private String msg;

    public DialogTuna(Activity a, String message) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.msg = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_tuna);
        ok = (View) findViewById(R.id.tv_dialog_ok);
        ok.setOnClickListener(this);

        TextView tvMsg = (TextView) findViewById(R.id.tv_dialog_msg);
        tvMsg.setText(this.msg);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dialog_ok:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
