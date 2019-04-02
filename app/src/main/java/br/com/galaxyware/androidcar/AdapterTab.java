package br.com.galaxyware.androidcar;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterTab extends FragmentPagerAdapter {
     private final List<Fragment> mFragmentList = new ArrayList<>();
     private final List<String> mFragmentTitleList =  new ArrayList<>();

     public void addFragment(Fragment aFragment, String title){
         mFragmentList.add(aFragment);
         mFragmentTitleList.add(title);
     }

    public AdapterTab(FragmentManager fm) {
        super(fm);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
