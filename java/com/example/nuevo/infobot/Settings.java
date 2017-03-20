package com.example.nuevo.infobot;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by shyam on 6/30/2016.
 */
public class Settings extends Fragment implements View.OnClickListener {
    Button save_id,change_pic;
    ImageButton but_pic1,but_pic2;
    EditText change_name;
    LinearLayout linearLayout,linearLayout2;
    public ChatArrayAdapter chatArrayAdapter;
    SessionManagement session;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings, container, false);
        session=new SessionManagement(getActivity().getApplicationContext());
        chatArrayAdapter = new ChatArrayAdapter(getActivity(),R.layout.activity_chat_ai);
        save_id=(Button) v.findViewById(R.id.save_id);
        change_name=(EditText)v.findViewById(R.id.change_name);
        change_pic=(Button) v.findViewById(R.id.change_pic);
        but_pic1=(ImageButton) v.findViewById(R.id.pic1);
        but_pic2=(ImageButton) v.findViewById(R.id.pic2);
        linearLayout=(LinearLayout)v.findViewById(R.id.lla);
        save_id.setOnClickListener(this);
        change_pic.setOnClickListener(this);
        but_pic1.setOnClickListener(this);
        but_pic2.setOnClickListener(this);
        change_name.setOnClickListener(this);
        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Settings");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.change_name:
                save_id.setVisibility(View.VISIBLE);
                break;
            case R.id.save_id:
                String new_name=change_name.getText().toString().trim();
                if(new_name.length()>0){
                    session.CreateChangeName(new_name);
                    ((DashboardNav)getActivity()).changeName(new_name);
                    Toast.makeText(getActivity(),"Name changed to "+new_name,Toast.LENGTH_SHORT).show();
                }
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                save_id.setVisibility(View.GONE);
                break;
            case R.id.change_pic:
                if(linearLayout.getVisibility()==View.GONE){
                    linearLayout.setVisibility(View.VISIBLE);
                }
                else{
                    linearLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.pic1:
                ((DashboardNav)getActivity()).changePic("one");
                chatArrayAdapter.notifyDataSetChanged();
                linearLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(),"Image changed",Toast.LENGTH_SHORT).show();
                break;
            case R.id.pic2:
                ((DashboardNav)getActivity()).changePic("two");
                chatArrayAdapter.notifyDataSetChanged();
                linearLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(),"Image changed",Toast.LENGTH_SHORT).show();
                break;
        }

    }
}