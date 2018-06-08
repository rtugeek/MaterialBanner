package com.freegeek.android.materialbanner.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


import com.freegeek.android.materialbanner.R;
import com.freegeek.android.materialbanner.holder.ViewHolderCreator;
import com.freegeek.android.materialbanner.holder.Holder;
import com.freegeek.android.materialbanner.view.MaterialViewPager;
import com.freegeek.android.materialbanner.view.indicator.IconPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Jack Fu <rtugeek@gmail.com>
 * @date 2016/9/20
 */
public class MaterialPageAdapter extends PagerAdapter implements IconPagerAdapter {

    protected List mData;
    protected List<Integer> mIcons = new ArrayList<>();
    protected ViewHolderCreator holderCreator;
    protected MaterialViewPager viewPager;


    @Override
    public int getIconResId(int index) {
        return mIcons.get(index % mIcons.size());
    }

    public void setIcons(List<Integer> icons) {
        this.mIcons = icons;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = getView(position, null, container);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        int position = viewPager.getCurrentItem();
        if (position == 0) {
            position = 0;
        } else if (position == getCount() - 1) {
            position = viewPager.getLastItem();
        }
        try {
            viewPager.setCurrentItem(position, false);
        } catch (IllegalStateException e) {
//            e.printStackTrace();
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    public void setViewPager(MaterialViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public MaterialPageAdapter(ViewHolderCreator holderCreator, List data) {
        this.holderCreator = holderCreator;
        this.mData = data;
    }

    public View getView(int position, View view, ViewGroup container) {
        Holder holder;
        if (view == null) {
            holder = (Holder) holderCreator.createHolder();
            view = holder.createView(container.getContext());
            view.setTag(R.id.cb_item_tag, holder);
        } else {
            holder = (Holder) view.getTag(R.id.cb_item_tag);
        }
        if (mData != null && !mData.isEmpty()) {
            holder.updateUI(container.getContext(), position, mData.get(position));
        }
        return view;
    }

}
