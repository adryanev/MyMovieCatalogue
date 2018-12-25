package com.adryanev.dicoding;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackBarUtils {
    public static void showLongSnackBar(View v, String snackbarText) {
        if(v==null || snackbarText == null){
            return;
        }
        Snackbar.make(v,snackbarText,Snackbar.LENGTH_LONG).show();

    }
    public static void showShortSnackBar(View v, String snackbarText) {
        if(v==null || snackbarText == null){
            return;
        }
        Snackbar.make(v,snackbarText,Snackbar.LENGTH_SHORT).show();

    }
}
