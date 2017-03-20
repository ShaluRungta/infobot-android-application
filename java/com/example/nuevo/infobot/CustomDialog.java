package com.example.nuevo.infobot;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by shyam on 7/18/2016.
 */
public class CustomDialog extends Dialog implements android.view.View.OnClickListener {
    public static final String CHAT_URL = "http://ec2-52-37-246-1.us-west-2.compute.amazonaws.com:60000/api/chat_data";
    public static final String KEY_QUES = "questions";
    public static final String KEY_ANS = "answers";
    public Activity c;
    public Dialog d;
    public Button add_ques, close;
    public EditText ques,ans;
    public String ques_text,ans_text,question;
    ProgressDialog pd;
    public CustomDialog(Activity a,String question) {
        super(a);
        this.c=a;
        this.question=question;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = ActionBar.LayoutParams.MATCH_PARENT;
        setCanceledOnTouchOutside(false);
        add_ques = (Button) findViewById(R.id.btn_add);
        close= (Button) findViewById(R.id.btn_close);
        ques=(EditText)findViewById(R.id.ques_id);
        ans=(EditText)findViewById(R.id.ans_id);
        if(!question.equals("")){
            ques.setText(question);
            ans.requestFocus();
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        add_ques.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                checkQuesAns();
                break;
            case R.id.btn_close:
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                dismiss();
                break;
            default:
                break;
        }
    }
    public void checkQuesAns(){
        if(ques.getText().toString().trim().equalsIgnoreCase("") || ans.getText().toString().trim().equalsIgnoreCase("")){
            if (ques.getText().toString().trim().equalsIgnoreCase("")) {
                ques.setError("Question field empty!");
            }
            if (ans.getText().toString().trim().equalsIgnoreCase("")) {
                ans.setError("Answer field empty!");
            }
        }
        else{
            pd=new ProgressDialog(getContext());
            pd.show();
            addQues();
        }
    }
    public void addQues(){
        ques_text=ques.getText().toString().trim();
        ans_text=ans.getText().toString().trim();
        HashMap<String, String> postData = new HashMap<>();
        //start data adding to the hash map to be send to the server
        postData.put(KEY_QUES,ques_text);
        postData.put(KEY_ANS,ans_text);
        VolleyCustomRequest objectRequest=new VolleyCustomRequest(Request.Method.POST, CHAT_URL, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            ques.setText("");
                            ans.setText("");
                            pd.dismiss();
                            Log.d("Ques Added", response.toString());
                            Toast.makeText(getContext(),"Ques added, Thanks!!",Toast.LENGTH_SHORT).show();
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
                            VolleyLog.d("Send Text", "nError: " + error.getMessage());
                            Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT ).show();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

        AppController.getInstance().addToRequestQueue(objectRequest);
    }
}
