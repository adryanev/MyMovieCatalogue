package com.adryanev.dicoding.mymoviecatalogue.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class ActivityUtils {

    public static void replaceFragment(@NonNull  FragmentManager fragmentManager,@NonNull Fragment fragment, int frameId){

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();

    }
    public static void replaceFragmentWithBackStack(@NonNull  FragmentManager fragmentManager,@NonNull Fragment fragment, int frameId){

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    public static void replaceFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, String tag) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragment, tag);
        transaction.commit();
    }
}
