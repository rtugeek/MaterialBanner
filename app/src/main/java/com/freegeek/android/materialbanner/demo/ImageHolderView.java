package com.freegeek.android.materialbanner.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.freegeek.android.materialbanner.holder.Holder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by leon on 2016/9/22.
 * Email:rtugeek@gmail.com
 */

public class ImageHolderView implements Holder<BannerData> {
    private ImageView imageView;
    private TextView title;
    private ProgressBar progressBar;
    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner,null);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        title = (TextView) view.findViewById(R.id.txt_title);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, BannerData data) {
        title.setText(data.getTitle());
        Picasso.with(context)
                .load(data.getUrl())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        progressBar.setVisibility(View.GONE);
                        imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }

}
