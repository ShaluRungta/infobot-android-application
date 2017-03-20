package com.example.nuevo.infobot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by shyam on 7/7/2016.
 */
public class SessionManagement {
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_PASS = "password";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAME = "user_name";
    public static final String IS_NAME = "IsHaveName";
    public static final String IS_PIC = "IsChangePic";
    public static final String KEY_IMG = "user pic";
    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences("Login_data", PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String password, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_PASS, password);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }

    public void CreateChangeName(String name){
        editor.putBoolean(IS_NAME,true);
        editor.putString(KEY_NAME,name);
        editor.commit();
    }
    public void createChangePic(String num){
        editor.putBoolean(IS_PIC,true);
        editor.putString(KEY_IMG,num);
        editor.commit();
    }
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }
    public HashMap<String,String> getUserName(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME,"InfoBot"));
        return user;
    }
    public HashMap<String,String> getUserPic(){
        HashMap<String, String> user_p = new HashMap<String, String>();
        // user pic
        user_p.put(KEY_IMG, pref.getString(KEY_IMG,null));
        return user_p;
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> uname = new HashMap<String, String>();
        // user password
        uname.put(KEY_PASS, pref.getString(KEY_PASS, null));

        // user email id
        uname.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return uname;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
    public boolean isChangePic(){
        return pref.getBoolean(IS_PIC,false);
    }
}
