package com.adryanev.dicoding.mymoviecatalogue.utils;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.support.design.animation.AnimationUtils;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.animation.Animation;


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
