package com.freegeek.android.materialbanner.demo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.freegeek.android.materialbanner.IndicatorGravity;
import com.freegeek.android.materialbanner.MaterialBanner;
import com.freegeek.android.materialbanner.holder.ViewHolderCreator;
import com.freegeek.android.materialbanner.view.indicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class SimpleActivity extends AppCompatActivity {

    private String[] images = {"http://static.panoramio.com/photos/large/132796978.jpg",
            "http://static.panoramio.com/photos/large/132796977.jpg",
            "http://static.panoramio.com/photos/large/133036192.jpg",
            "http://static.panoramio.com/photos/large/132796982.jpg",
            "http://static.panoramio.com/photos/large/132796981.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        //init data
        List<BannerData> bannerData = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            BannerData data = new BannerData();
            data.setTitle("Country road " + (i + 1));
            data.setUrl(images[i]);
            bannerData.add(data);
        }

        MaterialBanner materialBanner = (MaterialBanner) findViewById(R.id.material_banner);
        materialBanner.setPages(new ViewHolderCreator() {
                    @Override
                    public Object createHolder() {
                        return new ImageHolderView();
                    }
                },bannerData);
        //set indicator
        materialBanner.setIndicator(new CirclePageIndicator(this));


    }

    public void advanced(View view){
        startActivity(new Intent(this,MainActivity.class));
    }
}
