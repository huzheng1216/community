package com.flyersoft.discuss.shuhuang;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * creat by: huzheng
 * date: 2019/6/4
 * description:
 * 书单界面：发布/收藏
 */
public class BookListPageAdapter extends FragmentPagerAdapter {

    public final String[] tabTitles = new String[]{"发布"};
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    public BookListPageAdapter(FragmentManager fm) {
        super(fm);
        for (int i = 0; i < tabTitles.length; i++) {
            mFragments.add(new BookListFragment());
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
