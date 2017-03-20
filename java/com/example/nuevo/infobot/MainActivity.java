package com.example.nuevo.infobot;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button button_next;
    public static final String LOGIN_URL = "http://ec2-52-37-246-1.us-west-2.compute.amazonaws.com:60000/api/users/login";
    public static final String KEY_USERNAME = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String DEFAULT="N/A";
    public String email,password;
    private ProgressDialog pd;
    SessionManagement session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        internetError();
        session = new SessionManagement(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();

        // name
        password = user.get(SessionManagement.KEY_PASS);
        // email
        email = user.get(SessionManagement.KEY_EMAIL);
        button_next=(Button)findViewById(R.id.but_next);
        button_next.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        session.checkLogin();
                        if(session.isLoggedIn()){
                            pd=new ProgressDialog(MainActivity.this);
                            pd.setMessage("Loading...");
                            pd.show();
                            Thread th=new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        loginUser_auto();
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }

                                }
                            });
                            th.start();
                        }
                    }
                }
        );

    }
    public void internetError(){
        if(!isOnline(this)){
            DialogInternet di=new DialogInternet(this);
            di.show();
        }
    }
    public void loginUser_auto() {
        HashMap<String, String> postData = new HashMap<>();
        //start data adding to the hash map to be send to the server
        postData.put(KEY_USERNAME,email);
        postData.put(KEY_PASSWORD,password);

        VolleyCustomRequest objectRequest=new VolleyCustomRequest(Request.Method.POST, LOGIN_URL, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            pd.dismiss();
                            Log.d("Login Successful.", response.toString());
                            Intent intent = new Intent(MainActivity.this, DashboardNav.class);
                            intent.putExtra(KEY_USERNAME,email);
                            startActivity(intent);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try{

                            pd.dismiss();
                            VolleyLog.d("Login User", "nError: " + error.getMessage());
                            boolean isInternet=isOnline(MainActivity.this);
                            if(isInternet){
                                Toast.makeText(MainActivity.this,"Something's Wrong!! check connection maybe...",Toast.LENGTH_LONG).show();
                            }
                            else if(!isInternet){
                                internetError();
                            }
                            else
                                Toast.makeText(MainActivity.this,"Something's Not Right!! Please try again later...",Toast.LENGTH_SHORT ).show();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

        AppController.getInstance().addToRequestQueue(objectRequest);
    }
    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());

    }
}
