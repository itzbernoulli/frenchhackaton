package com.oyinloyeayodeji.www.frenchhackaton.Managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.oyinloyeayodeji.www.frenchhackaton.Objects.Shiuser;

/**
 * Created by Ayo on 04/05/2017.
 */

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "shicachodemo";
    private static final String KEY_ACCESS_TOKEN = "demo";

    private static Context mCtx;
    private static SharedPrefManager mInstance;
    private RequestQueue requestQueue;

    private SharedPrefManager(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static  synchronized SharedPrefManager getmInstance(Context context){
        if(mInstance == null)
            mInstance = new SharedPrefManager(context);
        return mInstance;
    }

    public boolean storeToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOKEN, token);
        editor.apply();
        return true;
    }

    public boolean storeUser(Shiuser user){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("FIRSTNAME", user.getmFirstname());
        editor.putString("LASTNAME", user.getmLastname());
        editor.putString("EMAIL", user.getmEmail());
        editor.putString("NETWORK", user.getmNetwork());
        editor.putString("IMAGE", user.getmImageUrl());
        editor.apply();
        return true;
    }

    public Shiuser getUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        Shiuser user = new Shiuser(
                sharedPreferences.getString("FIRSTNAME","null"),
                sharedPreferences.getString("LASTNAME","null"),
                sharedPreferences.getString("NETWORK","null"),
                sharedPreferences.getString("EMAIL","null"),
                sharedPreferences.getString("IMAGE", "null"));
        return user;
    }

    public String getToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ACCESS_TOKEN,"null token");
    }

    public<T> void addToRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }
}
