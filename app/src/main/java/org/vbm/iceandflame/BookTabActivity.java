package org.vbm.iceandflame;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;

public class BookTabActivity extends FragmentActivity {

    FragmentPagerAdapter bookShelf;
    PagerTitleStrip pagerTitleStrip;
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tab);

        Globals.databaseAccess.initCursor("select name from books order by _id");
        getWindow().setStatusBarColor(Color.BLACK);
        bookShelf = new mPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(bookShelf);

        pagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);

    }

}
