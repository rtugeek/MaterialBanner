package com.freegeek.android.materialbanner.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.freegeek.android.materialbanner.MaterialBanner;
import com.freegeek.android.materialbanner.adapter.MaterialPageAdapter;


public class MaterialViewPager extends ViewPager {
    OnPageChangeListener mOuterPageChangeListener;
    private MaterialBanner.OnItemClickListener onItemClickListener;
    private MaterialPageAdapter mAdapter;
    private boolean isCanScroll = true;

    public void setAdapter(PagerAdapter adapter) {
        mAdapter = (MaterialPageAdapter) adapter;
        mAdapter.setViewPager(this);
        super.setAdapter(mAdapter);

        setCurrentItem(0, false);
    }


    public int getLastItem() {
        return mAdapter.getCount() - 1;
    }

    public boolean isCanScroll() {
        return isCanScroll;
    }

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    private float oldX = 0, newX = 0;
    private static final float sens = 5;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            if (onItemClickListener != null) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        oldX = ev.getX();
                        break;

                    case MotionEvent.ACTION_UP:
                        newX = ev.getX();
                        if (Math.abs(oldX - newX) < sens) {
                            onItemClickListener.onItemClick(getItem());
                        }
                        oldX = 0;
                        newX = 0;
                        break;
                }
            }
            return super.onTouchEvent(ev);
        } else
            return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanScroll)
            return super.onInterceptTouchEvent(ev);
        else
            return false;
    }

    public MaterialPageAdapter getAdapter() {
        return mAdapter;
    }

    public int getItem() {
        return mAdapter != null ? super.getCurrentItem() : 0;
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mOuterPageChangeListener = listener;
    }


    public MaterialViewPager(Context context) {
        super(context);
        init();
    }

    public MaterialViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.setOnPageChangeListener(onPageChangeListener);
    }

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            if (mPreviousPosition != position) {
                mPreviousPosition = position;
                if (mOuterPageChangeListener != null) {
                    mOuterPageChangeListener.onPageSelected(position);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {

            if (mOuterPageChangeListener != null) {
                if (position != mAdapter.getCount() - 1) {
                    mOuterPageChangeListener.onPageScrolled(position,
                            positionOffset, positionOffsetPixels);
                } else {
                    if (positionOffset > .5) {
                        mOuterPageChangeListener.onPageScrolled(0, 0, 0);
                    } else {
                        mOuterPageChangeListener.onPageScrolled(position,
                                0, 0);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mOuterPageChangeListener != null) {
                mOuterPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };


    public void setOnItemClickListener(MaterialBanner.OnItemClickListener clickListener){
        onItemClickListener = clickListener;
    }
}
