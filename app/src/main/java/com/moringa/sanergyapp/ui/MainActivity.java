package com.moringa.sanergyapp.ui;



import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.auth.FirebaseAuth;
import com.moringa.sanergyapp.R;
import com.moringa.sanergyapp.adapters.PagerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.profile)
    TextView mProfile;
    @BindView(R.id.employees)
    TextView mEmployees;
    @BindView(R.id.notifications)
    TextView mNotifications;
    @BindView(R.id.mainPager)
    ViewPager mMainViewPager;

    private PagerViewAdapter mPagerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        mPagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager());
        mMainViewPager.setAdapter(mPagerViewAdapter);
        mMainViewPager.setOffscreenPageLimit(2);
        mMainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTabs(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainViewPager.setCurrentItem(0);
            }
        });
        mEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainViewPager.setCurrentItem(1);
            }
        });
        mNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainViewPager.setCurrentItem(0);
            }
        });
    }

    private void changeTabs(int position) {
        if(position == 0){
            mProfile.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorText));
            mProfile.setTextSize(22);

            mEmployees.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorTextLight));
            mEmployees.setTextSize(17);

            mNotifications.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorTextLight));
            mNotifications.setTextSize(17);
        }
        if(position == 1){
            mProfile.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorTextLight));
            mProfile.setTextSize(17);

            mEmployees.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorText));
            mEmployees.setTextSize(22);

            mNotifications.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorTextLight));
            mNotifications.setTextSize(17);
        }
        if(position == 2){
            mProfile.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorTextLight));
            mProfile.setTextSize(17);

            mEmployees.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorTextLight));
            mEmployees.setTextSize(17);

            mNotifications.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorText));
            mNotifications.setTextSize(22);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}