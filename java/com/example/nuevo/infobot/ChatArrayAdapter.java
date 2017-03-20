package com.example.nuevo.infobot;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by shyam on 7/13/2016.
 */
public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {
    private Button button;
    private TextView chatText,timeText;
    private ImageView imgSrc,imgAi;
    private static List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
    private Context context;
    public String ask;

    @Override
    public void add(ChatMessage object) {
        chatMessageList.add(object);
        super.add(object);
    }

    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public ChatMessage getItem(int index) {
        return this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ChatMessage chatMessageObj = getItem(position);
        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (chatMessageObj.left) {
            row = inflater.inflate(R.layout.custom_chat_user, parent, false);
            chatText = (TextView) row.findViewById(R.id.user_msg);
            timeText = (TextView) row.findViewById(R.id.timeuser_id);
            imgSrc=(ImageView)row.findViewById(R.id.userpic_id);
            chatText.setText(chatMessageObj.message);
            ask=chatMessageObj.message;
            timeText.setText(chatMessageObj.time);
            imgSrc.setBackgroundResource(chatMessageObj.imageId);
        }
        else{
            row = inflater.inflate(R.layout.custom_chat, parent, false);
            chatText = (TextView) row.findViewById(R.id.ai_msg);
            timeText = (TextView) row.findViewById(R.id.timeai_id);
            imgAi=(ImageView)row.findViewById(R.id.aipic_id);
            button=(Button)row.findViewById(R.id.teachai_id);
            if(chatMessageObj.message.equals("I donot know the answer. Click on 'add' button on the left of your chat to teach me.")){
                chatText.setText("I don't know the answer to that. Please Teach me.");
                button.setVisibility(View.VISIBLE);
                button.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CustomDialog dialog=new CustomDialog((Activity) view.getRootView().getContext(),ask);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.show();
                            }
                        }
                );
            }
            else {
                chatText.setText(chatMessageObj.message);
            }
            timeText.setText(chatMessageObj.time);
            imgAi.setBackgroundResource(chatMessageObj.imageId);
        }
        return row;
    }
}

