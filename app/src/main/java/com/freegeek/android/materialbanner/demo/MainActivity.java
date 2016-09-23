package com.freegeek.android.materialbanner.demo;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.freegeek.android.materialbanner.IndicatorGravity;
import com.freegeek.android.materialbanner.MaterialBanner;
import com.freegeek.android.materialbanner.holder.ViewHolderCreator;
import com.freegeek.android.materialbanner.holder.Holder;
import com.freegeek.android.materialbanner.view.indicator.CirclePageIndicator;
import com.freegeek.android.materialbanner.view.indicator.IconPageIndicator;
import com.freegeek.android.materialbanner.view.indicator.LinePageIndicator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String[] images = {"http://static.panoramio.com/photos/large/132796978.jpg",
            "http://static.panoramio.com/photos/large/132796977.jpg",
            "http://static.panoramio.com/photos/large/133036192.jpg",
            "http://static.panoramio.com/photos/large/132796982.jpg",
            "http://static.panoramio.com/photos/large/132796981.jpg"
    };

    private int[] iconIds = new int[]{android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_gallery,
            android.R.drawable.ic_menu_call,
            android.R.drawable.ic_menu_close_clear_cancel,
            android.R.drawable.ic_dialog_map};

    private MaterialBanner materialBanner;
    private TextView textView;

    private CirclePageIndicator circlePageIndicator;
    private LinePageIndicator linePageIndicator;
    private IconPageIndicator iconPageIndicator;


    List<BannerData> list = new ArrayList<>();
    List<Integer> icons = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        materialBanner = (MaterialBanner) findViewById(R.id.material_banner);
        textView = (TextView)findViewById(R.id.hometown);

        initIndicator();
        initData();

        materialBanner.setPages(
                new ViewHolderCreator<ImageHolderView>() {
                    @Override
                    public ImageHolderView createHolder() {
                        return new ImageHolderView();
                    }
                }, list)
        .setIndicator(circlePageIndicator);

        materialBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textView.setText("My hometown: page " + ++position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //iconPageIndicator request these icons
        materialBanner.getAdapter().setIcons(icons);

        materialBanner.setOnItemClickListener(new MaterialBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "click:" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initIndicator(){
        circlePageIndicator = new CirclePageIndicator(this);
        circlePageIndicator.setStrokeColor(Color.WHITE);
        circlePageIndicator.setFillColor(Color.WHITE);
        circlePageIndicator.setRadius(MaterialBanner.dip2Pix(this,3));
        circlePageIndicator.setBetween(20);

        linePageIndicator = new LinePageIndicator(this);
        linePageIndicator.setUnselectedColor(Color.WHITE);
        linePageIndicator.setSelectedColor(Color.YELLOW);

        iconPageIndicator = new IconPageIndicator(this);
    }

    private void initData(){
        for (int i = 0; i < images.length; i++) {
            BannerData bannerData = new BannerData();
            bannerData.setTitle("Country road " + (i + 1));
            bannerData.setUrl(images[i]);
            list.add(bannerData);
            icons.add(iconIds[i]);
        }
    }

    public void changeInside(View view){
        materialBanner.setIndicatorInside(!materialBanner.isIndicatorInside());
    }

    int index = 0;
    public void changeType(View view){
        switch (index % 3){
            case 2:
                Toast.makeText(this, "set CirclePageIndicator", Toast.LENGTH_SHORT).show();
                materialBanner.setIndicator(circlePageIndicator);
                break;
            case 1:
                Toast.makeText(this, "set LinePageIndicator", Toast.LENGTH_SHORT).show();
                materialBanner.setIndicator(linePageIndicator);
                break;
            case 0:
                Toast.makeText(this, "set IconPageIndicator", Toast.LENGTH_SHORT).show();
                materialBanner.setIndicator(iconPageIndicator);
                break;
        }
        index++;
    }

    int gravity = 0;
    public void changeGravity(View view){
        IndicatorGravity indicatorGravity = IndicatorGravity.valueOf(gravity);
        Toast.makeText(this, "gravity:" + indicatorGravity, Toast.LENGTH_SHORT).show();
        materialBanner.setIndicatorGravity(indicatorGravity);
        gravity++;
    }

    public void changeMatch(View view){
        materialBanner.setMatch(!materialBanner.isMatch());
    }

    public void changeTransformer(View view){
        materialBanner.setTransformer(new DepthPageTransformer());
    }


    public void turning(View view){
        if(materialBanner.isTurning()){
            materialBanner.stopTurning();
            Toast.makeText(this, "stop turning", Toast.LENGTH_SHORT).show();
        }else{
            materialBanner.startTurning(3000);
            Toast.makeText(this, "start turning", Toast.LENGTH_SHORT).show();
        }

    }

}
