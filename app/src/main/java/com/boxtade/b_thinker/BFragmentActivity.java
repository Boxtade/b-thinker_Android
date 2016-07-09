package com.boxtade.b_thinker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by kvins on 07/07/2016.
 */
public class BFragmentActivity extends FragmentActivity {

    protected void loadFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    protected void destroyFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.detach(fragment);
        transaction.remove(fragment).commit();
    }
}
