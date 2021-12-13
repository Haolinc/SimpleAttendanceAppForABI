package com.example.attendanceappgeneral;

import android.content.Context;
import android.content.SharedPreferences;

public class DataStorage {
    private final SharedPreferences sharedPreferences;

    public DataStorage(Context context){
        sharedPreferences = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE);
    }

    public void storeData(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public void storeData(String key, boolean value){
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public String getStringData(String key) {
        return sharedPreferences.getString(key, null);
    }

    public boolean getBooleanData(String key){
        return sharedPreferences.getBoolean(key, false);
    }
}
