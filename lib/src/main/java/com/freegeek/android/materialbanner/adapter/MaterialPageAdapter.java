package com.freegeek.android.materialbanner.adapter;

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
 * Created by leon on 2016/9/20.
 * Email:rtugeek@gmail.com
 */
public class MaterialPageAdapter<T> extends PagerAdapter implements IconPagerAdapter{
    protected List<T> mData;
    protected List<Integer> mIcons = new ArrayList<>();
    protected List<Integer> mIndicatorIcons;
    protected ViewHolderCreator holderCreator;
    private MaterialViewPager viewPager;


    @Override
    public int getIconResId(int index) {
        return mIcons.get(index % mIcons.size());
    }

    public void setIcons(List<Integer> icons){
        this.mIcons = icons;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(position, null, container);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
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
        }catch (IllegalStateException e){}
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    public void setViewPager(MaterialViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public MaterialPageAdapter(ViewHolderCreator holderCreator, List<T> datas) {
        this.holderCreator = holderCreator;
        this.mData = datas;
    }

    public View getView(int position, View view, ViewGroup container) {
        Holder holder = null;
        if (view == null) {
            holder = (Holder) holderCreator.createHolder();
            view = holder.createView(container.getContext());
            view.setTag(R.id.cb_item_tag, holder);
        } else {
            holder = (Holder<T>) view.getTag(R.id.cb_item_tag);
        }
        if (mData != null && !mData.isEmpty())
            holder.UpdateUI(container.getContext(), position, mData.get(position));
        return view;
    }

}
