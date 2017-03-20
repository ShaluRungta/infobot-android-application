package com.example.nuevo.infobot;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import static com.example.nuevo.infobot.R.id.frame_id;
import static com.example.nuevo.infobot.R.id.nav_aboutus;

public class DashboardNav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ProgressDialog pd;
    private long lastBackPressTime;
    TextView headername,headerid;
    String user,pass;
    SessionManagement session;
    static int t=0;
    static String name="Welcome";
    NavigationView navigationView;
    View hView;
    ImageView userimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.nav_dashboard, 0);
        }
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        hView =  navigationView.getHeaderView(0);
        session = new SessionManagement(getApplicationContext());

        HashMap<String, String> uname = session.getUserName();
        String user_name=uname.get(SessionManagement.KEY_NAME);
        changeName(user_name);
        Intent intent = getIntent();
        user = intent.getStringExtra(Login.KEY_USERNAME);
        if(user!=null){
            t=0;
        }
        if(t==0){
            name=user;
            if(!user_name.equals("InfoBot")){
                Toast.makeText(DashboardNav.this,"Welcome "+user_name,Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(DashboardNav.this,"Welcome "+user,Toast.LENGTH_SHORT).show();
            }
        }
        checkPicSession();
        headerid= (TextView)hView.findViewById(R.id.textView);
        headerid.setText(name);
        t++;
    }
    public void checkPicSession(){
        userimg=(ImageView)hView.findViewById(R.id.imageView);
        if(session.isChangePic()){
            HashMap<String, String> user_p = session.getUserPic();
            String num=user_p.get(SessionManagement.KEY_IMG);
            if(num.equals("one")){
                userimg=(ImageView)hView.findViewById(R.id.imageView);
                userimg.setBackgroundResource(R.drawable.boy_img);
            }
            else if(num.equals("two")){
                userimg=(ImageView)hView.findViewById(R.id.imageView);
                userimg.setBackgroundResource(R.drawable.girl_img);
            }
        }
        else{
            userimg.setBackgroundResource(R.drawable.infobot);
        }
    }
    public void changeName(String name){
        hView =  navigationView.getHeaderView(0);
        headername= (TextView)hView.findViewById(R.id.header_name);
        headername.setText(name);
    }
    public void changePic(String num){
        session.createChangePic(num);
        userimg=(ImageView)hView.findViewById(R.id.imageView);
        if(num.equals("one")){
            userimg=(ImageView)hView.findViewById(R.id.imageView);
            userimg.setBackgroundResource(R.drawable.boy_img);
        }
        else if(num.equals("two")){
            userimg=(ImageView)hView.findViewById(R.id.imageView);
            userimg.setBackgroundResource(R.drawable.girl_img);
        }
    }
   /* @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/
   @Override
   public void onBackPressed() {
// initialize variables
       FragmentManager fm = getFragmentManager();
       FragmentTransaction ft = fm.beginTransaction();
// check to see if stack is empty
       if (fm.getBackStackEntryCount() > 0) {
           fm.popBackStack();
           ft.commit();
       }
       //else {
       if (this.lastBackPressTime < System.currentTimeMillis() - 2000) {
           Toast.makeText(DashboardNav.this, "Please click BACK again to exit",Toast.LENGTH_SHORT).show();
           this.lastBackPressTime = System.currentTimeMillis();
       } else{
           moveTaskToBack(true);
           super.onBackPressed();
       }
       //}
   }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentmanager=getFragmentManager();
        if (id == R.id.nav_dashboard) {
            fragmentmanager.beginTransaction().replace(frame_id,new dashboard_scr()).commit();
        }
        else if (id == R.id.nav_setting) {
            fragmentmanager.beginTransaction().replace(frame_id,new Settings()).commit();
            /*FragmentTransaction tx = fragmentmanager.beginTransation();
            tx.replace( R.id.frame_id, new dashboard_scr() ).addToBackStack( "tag" ).commit();*/
        }
        else if (id == nav_aboutus) {
            fragmentmanager.beginTransaction().replace(frame_id,new AboutUs()).commit();
        }
        else if (id == R.id.nav_addQues) {
            CustomDialog cd=new CustomDialog(this,"");
            cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            cd.show();
        }
        else if (id == R.id.nav_logout) {
            makeSure("Log Out","Are you sure?");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void makeSure(String title,String msg){
        AlertDialog.Builder dialog= new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                pd= ProgressDialog.show(DashboardNav.this,"Logging Out","Please Wait");
                openLogin();
            }
        });
        dialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.show();
    }
    public void openLogin(){
        pd.dismiss();
        Toast.makeText(getBaseContext(), "Logged Out", Toast.LENGTH_SHORT).show();
        session.logoutUser();
        finish();
    }
}
