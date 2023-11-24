package com.example.doctruyenfirebase.Adapter;

// ImagePagerAdapter.java
// File: ImagePagerAdapter.java

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.doctruyenfirebase.R;

public class ImagePagerAdapter extends PagerAdapter {

    private Context mContext;
    private int[] mImageIds = {R.drawable.onepiece, R.drawable.dragonball, R.drawable.naruto, R.drawable.doremon};

    public ImagePagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_ad, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(mImageIds[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mImageIds.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
