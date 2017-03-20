package com.example.nuevo.infobot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Exams extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String EXAMS_URL="http://ec2-52-37-246-1.us-west-2.compute.amazonaws.com:60000/api/exams";
    private ProgressDialog dialog;
    private List<Item2> array=new ArrayList<Item2>();
    public ListView listView;
    private Adapter2 adapter;
    Button chatex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        chatex=(Button)findViewById(R.id.chatex_id);
        jsonRequest();
        clickBut();

    }
    public void clickBut(){
        chatex.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Exams.this,ChatAi.class));
                    }
                }
        );
    }
    public void jsonRequest(){
        listView=(ListView)findViewById(R.id.exams_id);
        adapter=new Adapter2(this,array);
        listView.setAdapter(adapter);
        dialog=new ProgressDialog(this);
        dialog.setMessage("loading...");
        dialog.show();
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(EXAMS_URL,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        hideDialog();
                        for(int i=0;i<response.length();i++){
                            try {
                                JSONObject obj=response.getJSONObject(i);
                                Item2 item=new Item2();
                                item.setSubject("Subject: "+obj.getString("subject"));
                                item.setYear("Year: "+obj.getString("year"));
                                item.setDate("Date: "+obj.getString("date"));
                                item.setTime("Time: "+obj.getString("time"));
                                item.setVenue("Venue: "+obj.getString("venue"));
                                array.add(item);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        hideDialog();
                        Toast.makeText(Exams.this,error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        hideDialog();
    }
    public void hideDialog(){
        if(dialog!=null){
            dialog.dismiss();
            dialog=null;
        }
    }

}
