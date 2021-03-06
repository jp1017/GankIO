package com.zhaochunqi.android.gankio.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.zhaochunqi.android.gankio.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 25/11/2016.
 */
public class ImageViewPagerAdapter extends PagerAdapter {
    private List<String> mResources = new ArrayList();
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Listener mListener;

    public ImageViewPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @FunctionalInterface
    public static interface Listener {
        public void onClick();
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public void setResources(List<String> resources) {
        mResources = resources;
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_image, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.image_display);

        Uri uri = Uri.parse(mResources.get(position));
        Glide.with(mContext)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.sample1)
                .into(imageView);

        container.addView(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClick();
                }
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
