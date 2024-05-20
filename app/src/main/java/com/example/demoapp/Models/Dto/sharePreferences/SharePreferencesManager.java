package com.example.demoapp.Models.Dto.sharePreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferencesManager {
    private static final String TOKEN_PREF_NAME = "MyTokenPref";
    private static final String TOKEN_KEY = "api_token";
    private static final String ID_KEY = "api_id";
    private static final String EMAIL_KEY = "email";
    private static final String STK_KEY = "stk";
    private static final String TOKEN_APP_KEY = "token_app";
    private static final String TOKEN_ID_LOAN = "loan";
    private static final String SAVING_ID_KEY = "saving_id";
    private static final String ACCOUNT_ID_KEY = "account_id";

    private SharedPreferences sharedPreferences;

    public SharePreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(TOKEN_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    public void clearToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOKEN_KEY);
        editor.apply();
    }
    public void saveUserId(int id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ID_KEY, id);
        editor.apply();
    }

    public int getUserId() {
        return sharedPreferences.getInt(ID_KEY, 0);
    }

    public void clearId() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ID_KEY);
        editor.apply();
    }

    public void saveEmal(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL_KEY, email);
        editor.apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(EMAIL_KEY, null);
    }


    public void clearEmail() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(EMAIL_KEY);
        editor.apply();
    }
    public void saveStk(String stk) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STK_KEY, stk);
        editor.apply();
    }

    public String getStk() {
        return sharedPreferences.getString(STK_KEY, null);
    }


    public void clearStk() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(STK_KEY);
        editor.apply();
    }
    public void saveTokenApp(String stk) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_APP_KEY, stk);
        editor.apply();
    }

    public String getTokenApp() {
        return sharedPreferences.getString(TOKEN_APP_KEY, null);
    }


    public void clearTokenApp() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOKEN_APP_KEY);
        editor.apply();
    }
    public void saveIdLoan(int stk) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TOKEN_ID_LOAN, stk);
        editor.apply();
    }

    public int getIdLoan() {
        return sharedPreferences.getInt(TOKEN_ID_LOAN, 0);
    }


    public void clearIdLoan() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOKEN_ID_LOAN);
        editor.apply();
    }

    public void saveSavingId(int stk) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SAVING_ID_KEY, stk);
        editor.apply();
    }

    public int getSavingId() {
        return sharedPreferences.getInt(SAVING_ID_KEY, 0);
    }


    public void clearSavingId() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(SAVING_ID_KEY);
        editor.apply();
    }
    public void saveAccountId(int stk) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ACCOUNT_ID_KEY, stk);
        editor.apply();
    }

    public int getAccountId() {
        return sharedPreferences.getInt(ACCOUNT_ID_KEY, 0);
    }


    public void clearAccountId() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ACCOUNT_ID_KEY);
        editor.apply();
    }

}
