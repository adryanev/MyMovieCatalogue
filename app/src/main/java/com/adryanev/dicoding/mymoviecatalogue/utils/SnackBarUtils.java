package com.adryanev.dicoding.mymoviecatalogue.utils;

import com.google.android.material.snackbar.Snackbar;
import android.view.View;

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
