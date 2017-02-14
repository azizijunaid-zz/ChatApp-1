package com.example.qasimnawaz.chatappp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.qasimnawaz.chatappp.Fragments.ChatFragment;
import com.example.qasimnawaz.chatappp.Fragments.ContactsFragment;

/**
 * Created by Qasim Nawaz on 2/1/2017.
 */

public class PAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ChatFragment tab1 = new ChatFragment();
                return tab1;
            case 1:
                ContactsFragment tab2 = new ContactsFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
