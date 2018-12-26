package com.adryanev.dicoding.mymoviecatalogue.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor  editor;
    private static final String PREF_NAME = "dikamus_preference";
    private static final String KEY_IS_FIRST = "isFirst";
    public SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }
    public void setFirstLaunch(boolean isFirst){
        editor.putBoolean(KEY_IS_FIRST, isFirst);
        editor.apply();
    }
    public boolean isFirstLaunch(){
        return  sharedPreferences.getBoolean(KEY_IS_FIRST,true);
    }

}
