package com.freegeek.android.materialbanner.simple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.freegeek.android.materialbanner.R;
import com.freegeek.android.materialbanner.holder.Holder;

/**
 * @author Jack Fu <rtugeek@gmail.com>
 * @date 2016/9/22
 */
public class SimpleHolder implements Holder<SimpleBannerData> {
    private ImageView imageView;
    private TextView title;
    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.simple_banner_item,null);
        imageView = view.findViewById(R.id.imageView);
        title = view.findViewById(R.id.txt_title);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return view;
    }

    @Override
    public void updateUI(Context context, int position, SimpleBannerData data) {
        title.setText(data.getTitle());
        imageView.setImageResource(data.getResId());
    }

}
