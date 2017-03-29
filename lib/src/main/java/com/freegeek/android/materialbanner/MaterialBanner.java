/*
 *                DO WHAT YOU WANT TO PUBLIC LICENSE
 *                     Version 3, January 2016
 *
 *  Copyright (C) 2016 Leon Fu <rtugeek@gmail.com>
 *
 *  Everyone is permitted to copy and distribute verbatim or modified
 *  copies of this license document, and changing it is allowed as long
 *  as the name is changed.
 *
 *                 DO WHAT YOU WANT TO PUBLIC LICENSE
 *    TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *      0. You just DO WHAT YOU WANT TO.
 *
 */

package com.freegeek.android.materialbanner;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.freegeek.android.materialbanner.adapter.MaterialPageAdapter;
import com.freegeek.android.materialbanner.holder.ViewHolderCreator;
import com.freegeek.android.materialbanner.view.MaterialViewPager;
import com.freegeek.android.materialbanner.view.indicator.PageIndicator;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Jack on 2016/9/20.
 * Email:rtugeek@gmail.com
 */
public class MaterialBanner<T> extends FrameLayout{
    private MaterialViewPager mViewPager;
    private PageIndicator mPageIndicator;
    private List<T> mData;
    private MaterialPageAdapter mPageAdapter;
    private ViewPagerScroller scroller;
    private long autoTurningTime;
    private boolean turning;
    private boolean canTurn = false;
    private int mIndicatorMargin = 0;
    private FrameLayout mIndicatorContainer;
    private FrameLayout mCardContainer;
    private FrameLayout.LayoutParams mIndicatorParams;
    private FrameLayout.LayoutParams mCardParams;
    private boolean mIndicatorInside = false;
    private boolean mMatch = false;
    private CardView mCardView;
    private IndicatorGravity mIndicatorGravity = IndicatorGravity.LEFT;
    public MaterialBanner(Context context) {
        super(context);
        init(context);
    }

    public MaterialBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public MaterialBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaterialBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context){
        init(context,null);
    }

    private void init(Context context,AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.MaterialBanner);
        mIndicatorMargin = (int) a.getDimension(R.styleable.MaterialBanner_indicatorMargin,dip2Pix(context,10));
        mIndicatorGravity = IndicatorGravity.valueOf((int) a.getInt(R.styleable.MaterialBanner_indicatorGravity,0));
        mIndicatorInside = a.getBoolean(R.styleable.MaterialBanner_indicatorInside,true);
        mMatch = a.getBoolean(R.styleable.MaterialBanner_match,false);
        a.recycle();

        View view = LayoutInflater.from(context).inflate(R.layout.material_banner,this,true);

        mCardView = (CardView)view.findViewById(R.id.card_view);
        mViewPager = (MaterialViewPager) view.findViewById(R.id.view_pager);
        mCardContainer = (FrameLayout) view.findViewById(R.id.container);
        mCardContainer = (FrameLayout) view.findViewById(R.id.card_container);
        mIndicatorContainer = (FrameLayout) view.findViewById(R.id.indicator_container);

        mIndicatorParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mIndicatorParams.gravity = Gravity.CENTER;

        mCardParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mCardParams.gravity = Gravity.TOP;

        //set Z value. bring indicator view to front,view.bringToFront does't work on 6.0
        ViewCompat.setZ(mCardView,1);
        ViewCompat.setZ(mViewPager,2);

        ViewCompat.setZ(mCardContainer,1);
        ViewCompat.setZ(mIndicatorContainer,2);

        updateMargin();
        setMatch(mMatch);
        adSwitchTask = new AdSwitchTask(this);

    }

    private void updateIndicatorMargin(){
        mIndicatorParams.setMargins(mIndicatorMargin,mIndicatorMargin,mIndicatorMargin,mIndicatorMargin);
        if(mPageIndicator != null)mPageIndicator.getView().setLayoutParams(mIndicatorParams);
    }

    public MaterialBanner setIndicatorGravity(IndicatorGravity indicatorGravity) {
        this.mIndicatorGravity = indicatorGravity;
        mIndicatorParams.gravity = IndicatorGravity.toGravity(indicatorGravity);
        mPageIndicator.getView().setLayoutParams(mIndicatorParams);
        return this;
    }

    public MaterialBanner setIndicator(PageIndicator pageIndicator){
        if(mPageIndicator == pageIndicator) return this;

        //remove old indicator view first;
        if(mPageIndicator != null) mIndicatorContainer.removeView(mPageIndicator.getView());
        mPageIndicator = pageIndicator;
        mPageIndicator.setViewPager(mViewPager);
        mPageIndicator.setCurrentItem(getCurrentItem());
        mIndicatorContainer.addView(mPageIndicator.getView(),mIndicatorParams);
        //update listener
        setOnPageChangeListener(mOnPageChangeListener);
        //get the real height then update margin;
        ViewTreeObserver observer = mIndicatorContainer.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                updateMargin();
            }
        });
        return this;
    }

    public MaterialBanner setIndicatorMargin(int unit){
        mIndicatorMargin = unit;
        updateIndicatorMargin();
        return this;
    }

    public MaterialBanner setIndicatorInside(boolean inside){
        mIndicatorInside = inside;
        updateMargin();
        return this;
    }

    public boolean isIndicatorInside(){
        return mIndicatorInside;
    }


    /**
     * update the margin value of indicator and cardContainer
     */
    private void updateMargin(){
        if(mPageIndicator == null) return;
        updateIndicatorMargin();
        if(!mIndicatorInside){
            //set margin according to the mIndicatorContainer height
            mCardParams.bottomMargin = mIndicatorContainer.getHeight();
        }else{
            mCardParams.bottomMargin = 0;
        }
        mCardContainer.setLayoutParams(mCardParams);
        invalidate();
    }


    public MaterialBanner setOnItemClickListener(OnItemClickListener onItemClickListener){
        if (onItemClickListener == null) {
            mViewPager.setOnItemClickListener(null);
            return this;
        }
        mViewPager.setOnItemClickListener(onItemClickListener);
        return this;
    }

    public void setAdapter(MaterialPageAdapter adapter){
        mPageAdapter = adapter;
        mViewPager.setAdapter(adapter);
        if(mPageIndicator != null) mPageIndicator.setViewPager(mViewPager);
    }


    public MaterialPageAdapter getAdapter(){
        return mPageAdapter ;
    }

    private AdSwitchTask adSwitchTask ;


    static class AdSwitchTask implements Runnable {

        private final WeakReference<MaterialBanner> reference;

        AdSwitchTask(MaterialBanner MaterialBanner) {
            this.reference = new WeakReference<MaterialBanner>(MaterialBanner);
        }

        @Override
        public void run() {
            MaterialBanner materialBanner = reference.get();

            if(materialBanner != null){
                if (materialBanner.mViewPager != null && materialBanner.turning) {
                    int page = materialBanner.mViewPager.getCurrentItem() + 1;
                    if(page >= materialBanner.mData.size())  page = 0;
                    materialBanner.mViewPager.setCurrentItem(page % materialBanner.mData.size());
                    materialBanner.postDelayed(materialBanner.adSwitchTask, materialBanner.autoTurningTime);
                }
            }
        }
    }

    public MaterialBanner setPages(ViewHolderCreator holderCreator, List<T> data){
        this.mData = data;
        mPageAdapter = new MaterialPageAdapter(holderCreator, mData);
        mViewPager.setAdapter(mPageAdapter);

        if(mPageIndicator != null)mPageIndicator.setViewPager(mViewPager);
        return this;
    }


    public void notifyDataSetChanged(){
        mViewPager.getAdapter().notifyDataSetChanged();
    }


    public boolean isTurning() {
        return turning;
    }


    public MaterialBanner startTurning(long autoTurningTime) {
        if(turning){
            stopTurning();
        }
        canTurn = true;
        this.autoTurningTime = autoTurningTime;
        turning = true;
        postDelayed(adSwitchTask, autoTurningTime);
        return this;
    }

    public void stopTurning() {
        turning = false;
        removeCallbacks(adSwitchTask);
    }


    /**
     *
     * @param transformer
     * @return
     */
    public MaterialBanner setTransformer(ViewPager.PageTransformer transformer) {
        mViewPager.setPageTransformer(true, transformer);
        return this;
    }


    public boolean isManualPageable() {
        return mViewPager.isCanScroll();
    }

    public void setManualPageable(boolean manualPageable) {
        mViewPager.setCanScroll(manualPageable);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP||action == MotionEvent.ACTION_CANCEL||action == MotionEvent.ACTION_OUTSIDE) {
            if (canTurn)startTurning(autoTurningTime);
        } else if (action == MotionEvent.ACTION_DOWN) {
            if (canTurn)stopTurning();
        }
        return super.dispatchTouchEvent(ev);
    }

    public int getCurrentItem(){
        if (mViewPager !=null) {
            return mViewPager.getItem();
        }
        return -1;
    }
    public void setCurrentItem(int index){
        if (mViewPager !=null) {
            mViewPager.setCurrentItem(index);
        }
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    public MaterialBanner setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
        if(mPageIndicator != null ){
            mPageIndicator.setOnPageChangeListener(onPageChangeListener);
            mViewPager.setOnPageChangeListener(mPageIndicator);
        }else{
            mViewPager.setOnPageChangeListener(onPageChangeListener);
        }
        return this;
    }

    public MaterialViewPager getViewPager() {
        return mViewPager;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    /**
     * whether banner match parent or not. In other word make the viewpager cover the cardView.
     * @param match
     */
    public MaterialBanner setMatch(boolean match){
        mMatch = match;
        if(mMatch){
            if(!mViewPager.getParent().equals(mCardContainer)){
                mCardView.removeView(mViewPager);
                mCardContainer.addView(mViewPager, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
            }
        }else{
            if(!mViewPager.getParent().equals(mCardView)){
                mCardContainer.removeView(mViewPager);
                mCardView.addView(mViewPager, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
            }
        }
        return this;
    }

    public boolean isMatch(){
        return mMatch;
    }


    public static int dip2Pix(Context context,float dip){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public CardView getCardView(){
        return mCardView;
    }

    public FrameLayout getIndicatorContainer(){
        return mIndicatorContainer;
    }


}
