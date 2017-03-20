package com.example.nuevo.infobot;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ChatAi extends AppCompatActivity {
    public String CHAT_URL = "http://ec2-52-37-246-1.us-west-2.compute.amazonaws.com:60000/api/chat_data/chatting?question=";
    static int t=0;
    public int c=0;
    EditText msg;
    Button button;
    String text;
    public ListView listView;
    private static ChatArrayAdapter chatArrayAdapter;
    private boolean side =true;
    SessionManagement session;
    public int img,imgai;
    String time,airesponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_ai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        session = new SessionManagement(getApplicationContext());
        msg=(EditText)findViewById(R.id.msg_id);
        button=(Button)findViewById(R.id.send_id);
        listView=(ListView)findViewById(R.id.chat_list);
        msg.setHint(Html.fromHtml("<small>" + "Write message here..." + "</small>" ));
        imgai=R.drawable.infobot;
        if(session.isChangePic()){
            final HashMap<String, String> user_p = session.getUserPic();
            String num=user_p.get(SessionManagement.KEY_IMG);
            if(num.equals("one")){
                img=R.drawable.boy_img;
            }
            else if(num.equals("two")){
                img=R.drawable.girl_img;
            }
        }
        else{
            img=R.drawable.nouserpic;
        }
        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(),R.layout.activity_chat_ai);
        listView.setAdapter(chatArrayAdapter);
        if(t==0){
            sendAiMessageBfr();
            t++;
        }
        msg.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        text=msg.getText().toString().trim();
                        if(text.length()>0) {
                            sendChatMessage();
                        }
                    }
                }
        );
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);
        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
        chatArrayAdapter.notifyDataSetChanged();
    }
    private boolean sendAiMessage(String ai_rply){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        time=sdf.format(cal.getTime());
        chatArrayAdapter.add(new ChatMessage(!side,ai_rply,imgai,time));
        return true;
    }
    private boolean sendAiMessageBfr(){

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        time=sdf.format(cal.getTime());
        chatArrayAdapter.add(new ChatMessage(!side,"Hey, I am InfoBot. You can ask me anything related to your institute.",imgai,time));
        return true;
    }
    private boolean sendChatMessage() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        time=sdf.format(cal.getTime());
        chatArrayAdapter.add(new ChatMessage(side, msg.getText().toString(),img,time));
        String message=msg.getText().toString();
        message=message.replaceAll("%","%25");
        message=message.replaceAll(" ","%20");
        message=message.replace("?","%3F");
        String Url=CHAT_URL+message;
        sendToApi(Url);
        msg.setText("");
        return true;
    }

    public void sendToApi(final String Url){
        StringRequest strReq = new StringRequest(Request.Method.POST,Url,
                new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Response: ", response.toString());
                airesponse=response.toString();
                sendAiMessage(airesponse.substring(1,airesponse.length()-1));

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Chat","Error: " + error.getMessage());
                Toast.makeText(ChatAi.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(strReq);

    }
}
