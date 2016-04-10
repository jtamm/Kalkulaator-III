package com.example.androidstudio.kalkulaatoriii;

import android.support.v4.view.PagerAdapter;
import android.view.View;

public class WizardPagerAdapter extends PagerAdapter {
    @Override
    public Object instantiateItem(View collection, int position) {

        int resId = 0;
        switch (position) {
            case 0:
                resId = R.id.page1;
                break;
            case 1:
                resId = R.id.page2;
                break;
            case 2:
                resId = R.id.page3;
                break;
        }
        return collection.findViewById(resId);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }
}