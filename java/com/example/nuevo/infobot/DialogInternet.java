package com.example.nuevo.infobot;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
/**
 * Created by shyam on 7/18/2016.
 */
public class DialogInternet extends Dialog implements View.OnClickListener{
    public Activity c;
    Button reload;
    public DialogInternet(Activity a) {
        super(a);
        this.c=a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_internet);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = ActionBar.LayoutParams.MATCH_PARENT;
        setCanceledOnTouchOutside(false);
        reload=(Button)findViewById(R.id.btn_reload);
        reload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(isOnline(getContext())){
            dismiss();
        }

    }
    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());

    }
}
