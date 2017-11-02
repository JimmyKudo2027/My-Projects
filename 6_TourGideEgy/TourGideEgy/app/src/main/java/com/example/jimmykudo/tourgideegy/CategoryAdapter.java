package com.example.jimmykudo.tourgideegy;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Jimmy Kudo on 10/16/2017.
 */

public class CategoryAdapter extends FragmentPagerAdapter {
    Context mContext;
    public CategoryAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new CairoGizaFragment();
        } else if (position == 1) {
            return new LuxorFragment();
        } else if (position == 2) {
            return new AswanFragment();
        } else {
            return new AlexFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.CairoGiza);
        } else if (position == 1) {
            return mContext.getString(R.string.Luxor);
        } else if (position == 2) {
            return mContext.getString(R.string.Aswan);
        } else {
            return mContext.getString(R.string.Alex);
        }
    }
}
