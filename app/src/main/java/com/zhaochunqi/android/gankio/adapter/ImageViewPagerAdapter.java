package com.zhaochunqi.android.gankio.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
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

        SimpleDraweeView imageView = (SimpleDraweeView) itemView.findViewById(R.id.image_display);

        Uri uri = Uri.parse(mResources.get(position));
        imageView.setImageURI(uri);

        container.addView(itemView);

        itemView.setOnClickListener(listener -> {
            if (mListener != null) {
                mListener.onClick();
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
