package com.example.nuevo.infobot;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by shyam on 6/30/2016.
 */
public class dashboard_scr extends Fragment implements View.OnClickListener{
    Button but_events,but_quiz,but_exams;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard, container, false);
        but_events=(Button)view.findViewById(R.id.but_events);
        but_quiz=(Button)view.findViewById(R.id.but_quiz);
        but_exams=(Button)view.findViewById(R.id.but_exams);
        but_events.setOnClickListener(this);
        but_exams.setOnClickListener(this);
        but_quiz.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.but_events:
                startActivity(new Intent(getActivity(),Events.class));
                break;

            case R.id.but_exams:
                startActivity(new Intent(getActivity(),Exams.class));
                break;

            case R.id.but_quiz:
                startActivity(new Intent(getActivity(),Quiz.class));
                break;

            default:
                break;
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("DashBoard");
    }
}