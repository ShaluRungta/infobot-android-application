package com.example.nuevo.infobot;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener {
    public static final String LOGIN_URL = "http://ec2-52-37-246-1.us-west-2.compute.amazonaws.com:60000/api/users/login";
    public static final String KEY_USERNAME = "email";
    public static final String KEY_PASSWORD = "password";
    EditText web_id, pass_id;
    Button button_login;
    public String username;
    public String password;
    private ProgressDialog pd;
    CheckBox checkBox;
    SessionManagement session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        session = new SessionManagement(getApplicationContext());
        web_id = (EditText) findViewById(R.id.webmail_id);
        pass_id = (EditText) findViewById(R.id.password_id);
        button_login = (Button) findViewById(R.id.but_login);
        checkBox=(CheckBox)findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(checkBox.isChecked()) {
                            pass_id.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        } else {
                            pass_id.setInputType(129);
                        }
                    }
                }
        );
        button_login.setOnClickListener(this);

    }
    int chk;
    public boolean checkWebId() {
        String webid_text = web_id.getText().toString().trim();
        String pass_text = pass_id.getText().toString().trim();
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(webid_text);
        if (webid_text.length() < 1 || pass_text.length() < 1) {
            if (web_id.getText().toString().trim().equalsIgnoreCase("") ) {
                web_id.setError("This field can not be blank");
            }
            if (pass_id.getText().toString().trim().equalsIgnoreCase("")) {
                pass_id.setError("This field can not be blank");
            }
            chk=1;
            return false;
        } else if (m.matches()) {
            return true;
        } else {
            web_id.setError("Invalid email form");
            return false;
        }
    }

    public boolean checkPass() {
        String pass_text = pass_id.getText().toString().trim();
        if(pass_text.length() > 5) { chk=1;
            return true;
        }
        else if(chk==0){
            pass_id.setError("Password must be at least of six characters");
            return false;
        }
        else
            return false;
    }

    public void loginUser() {
        username = web_id.getText().toString().trim();
        password = pass_id.getText().toString().trim();
        HashMap<String, String> postData = new HashMap<>();
        //start data adding to the hash map to be send to the server
        postData.put(KEY_USERNAME,username);
        postData.put(KEY_PASSWORD,password);
        VolleyCustomRequest objectRequest=new VolleyCustomRequest(Request.Method.POST, LOGIN_URL, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            session.createLoginSession(password,username);
                            pd.dismiss();
                            Log.d("Login Successful.", response.toString());
                            openActivity();
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
                            boolean isInternet=isOnline(Login.this);
                            if(isInternet){
                                Toast.makeText(Login.this,"Something's Wrong!! Check id and password",Toast.LENGTH_LONG).show();
                            }
                            else if(!isInternet){
                                DialogInternet di=new DialogInternet(Login.this);
                                di.show();
                            }
                            else
                                Toast.makeText(Login.this,"Something's Not Right!! Please try again later...",Toast.LENGTH_SHORT ).show();
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

    public void openActivity() {
        Intent intent = new Intent(Login.this, DashboardNav.class);
        intent.putExtra(KEY_USERNAME, username);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        chk=0;
        boolean isOkid = checkWebId();
        boolean isOkpass = checkPass();
        if (isOkid && isOkpass){
            pd= ProgressDialog.show(Login.this,"Logging In","Please Wait");
            Thread th=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        loginUser();
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
