package com.example.todourmat.presentation.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.todourmat.R;
import com.example.todourmat.data.AppPreferences;

import com.example.todourmat.model.BoredAction;
import com.example.todourmat.presentation.favourites.FavAdapter;
import com.example.todourmat.presentation.favourites.FavouritesFragment;
import com.example.todourmat.presentation.intro.IntroActivity;
import com.example.todourmat.presentation.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private FavAdapter favAdapter;
    private ArrayList<BoredAction> list = new ArrayList<>();


    public void skipIntroIfShown() {
        AppPreferences appPreferences = new AppPreferences(this);
        boolean isFirstLaunched = appPreferences.isFirstLaunch();
        if (isFirstLaunched) {
            startActivity(new Intent(this, IntroActivity.class));
            appPreferences.setLaunched();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skipIntroIfShown();
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.main_view_pager);
        bottomNavigationView = findViewById(R.id.main_bottom_navigation);

        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);

        list = new ArrayList<>();
        favAdapter = new FavAdapter(list);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_main:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.nav_favourites:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.nav_settings:
                    viewPager.setCurrentItem(2);
                    break;
            }
            return false;
        });
        bottomNavigationView.setItemIconSize(12);
    }

    public class MainPagerAdapter extends FragmentPagerAdapter {

        public MainPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        public MainPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = MainFragment.newInstance();
                    favAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    fragment = FavouritesFragment.newInstance();
                    favAdapter.notifyDataSetChanged();

                    break;
                default:
                    fragment = SettingsFragment.newInstance();
                    favAdapter.notifyDataSetChanged();

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}