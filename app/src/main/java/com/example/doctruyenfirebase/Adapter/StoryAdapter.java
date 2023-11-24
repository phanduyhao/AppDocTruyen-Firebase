// StoryAdapter.java
package com.example.doctruyenfirebase.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.doctruyenfirebase.R;
import com.example.doctruyenfirebase.model.Story;

import java.util.List;

public class StoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<Story> truyenList;
    public void setStoryList(List<Story> truyenList) {
        this.truyenList = truyenList;
    }
    public StoryAdapter(Context context, List<Story> truyenList) {
        this.mContext = context;
        this.truyenList = truyenList;
    }

    @Override
    public int getCount() {
        return truyenList.size();
    }

    @Override
    public Object getItem(int position) {
        return truyenList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.story_list_item, null);

        ImageView thumbnailImageView = view.findViewById(R.id.imageViewStoryThumbnail);
        TextView tenChapTextView = view.findViewById(R.id.txvTenChap);
        TextView tenTruyenTextView = view.findViewById(R.id.textViewStoryName);

        Story truyen = truyenList.get(position);

        // Load image using Glide
        Glide.with(mContext).load(truyen.getAnh()).into(thumbnailImageView);

        // Set other data
        tenChapTextView.setText(truyen.getDanhmuc());  // You need to get actual chapter data
        tenTruyenTextView.setText(truyen.getTentruyen());

        return view;
    }
}
