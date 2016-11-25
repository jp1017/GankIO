package com.zhaochunqi.android.gankio;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.zhaochunqi.android.gankio.adapter.ContentFragmentPagerAdapter;

public class MainActivity extends BaseActivity {

    private ContentFragmentPagerAdapter mPagerAdapter;
    public static final String CURRENT_POSITION = "currentPositon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = 0;
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt(CURRENT_POSITION);
        }
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new ContentFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setCurrentItem(position);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        int position = mPagerAdapter.getPostion();
        outState.putInt(CURRENT_POSITION, position);
    }
}
