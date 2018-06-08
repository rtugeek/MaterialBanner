package com.freegeek.android.materialbanner.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.freegeek.android.materialbanner.MaterialBanner;
import com.freegeek.android.materialbanner.holder.ViewHolderCreator;
import com.freegeek.android.materialbanner.simple.SimpleBannerData;
import com.freegeek.android.materialbanner.simple.SimpleHolder;
import com.freegeek.android.materialbanner.view.indicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Jack Fu <rtugeek@gmail.com>
 * @date 2018/06/08
 */
public class SimpleActivity extends AppCompatActivity {

    private static int[] images = {R.drawable.home_1,
            R.drawable.home_2,
            R.drawable.home_3,
            R.drawable.home_4,
            R.drawable.home_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        //init data
        List<SimpleBannerData> simpleBannerData = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            SimpleBannerData data = new SimpleBannerData();
            data.setTitle("Country road " + (i + 1));
            data.setResId(images[i]);
            simpleBannerData.add(data);
        }

        MaterialBanner materialBanner = findViewById(R.id.material_banner);
        materialBanner.setPages(new ViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new SimpleHolder();
            }
        }, simpleBannerData);
        //set indicator
        materialBanner.setIndicator(new CirclePageIndicator(this));


    }

    public void advanced(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void advanced2(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }
}
