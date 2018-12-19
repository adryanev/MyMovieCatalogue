package com.adryanev.dicoding.mymoviecatalogue.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackBarUtils {
    public static void showSnackBar(View v, String snackbarText) {
        if(v==null || snackbarText == null){
            return;
        }
        Snackbar.make(v,snackbarText,Snackbar.LENGTH_LONG).show();

    }
}
