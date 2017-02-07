package org.vbm.iceandflame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by vbm on 06/02/2017.
 */

public class mPagerAdapter extends FragmentStatePagerAdapter {


    public mPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return Globals.databaseAccess.cursor.getCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Globals.databaseAccess.cursor.moveToPosition(position);
        return Globals.databaseAccess.cursor.getString(0);
    }


    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putInt(mPagerFragment.KEY_POSITION, position);
        //args.putString(mPagerFragment.KEY_NAME,getCharacters(position).toString());
        mPagerFragment fragment = new mPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
