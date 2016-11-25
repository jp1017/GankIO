package com.zhaochunqi.android.gankio.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhaochunqi.android.gankio.R;
import com.zhaochunqi.android.gankio.WebViewActivity;
import com.zhaochunqi.android.gankio.beans.Content;

import java.util.List;

/**
 * Created by developer on 25/11/2016.
 */

public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Content> mContents;
    private Context mContext;

    public static enum ITEM_TYPE {
        VIEW_TYPE_IMAGE,
        VIEW_TYPE_TEXT
    }

    public ContentAdapter(List<Content> contents) {
        mContents = contents;
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvTitle;
        private final TextView mTvAuthor;
        private final TextView mTvPubDate;
        private final RelativeLayout mRelativeLayout;

        public TextViewHolder(View itemView) {
            super(itemView);

            mTvTitle = ((TextView) itemView.findViewById(R.id.tv_title));
            mTvAuthor = ((TextView) itemView.findViewById(R.id.tv_author));
            mTvPubDate = ((TextView) itemView.findViewById(R.id.tv_pub_date));
            mRelativeLayout = ((RelativeLayout) itemView.findViewById(R.id.holder));
        }

    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvTitle;
        private final TextView mTvAuthor;
        private final TextView mTvPubDate;
        private final RelativeLayout mRelativeLayout;
        private final ViewPager mVpImage;

        public ImageViewHolder(View itemView) {
            super(itemView);

            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvAuthor = ((TextView) itemView.findViewById(R.id.tv_author));
            mTvPubDate = ((TextView) itemView.findViewById(R.id.tv_pub_date));
            mRelativeLayout = ((RelativeLayout) itemView.findViewById(R.id.holder));
            mVpImage = (ViewPager) itemView.findViewById(R.id.vp_image);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ITEM_TYPE.VIEW_TYPE_TEXT.ordinal()) {
            View textContentView = inflater.inflate(R.layout.item_text, parent, false);
            viewHolder = new TextViewHolder(textContentView);
        }

        if (viewType == ITEM_TYPE.VIEW_TYPE_IMAGE.ordinal()) {
            View imageContentView = inflater.inflate(R.layout.item_image, parent, false);
            viewHolder = new ImageViewHolder(imageContentView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Content content = mContents.get(position);

        if (holder instanceof TextViewHolder) {
            TextViewHolder textViewHolder = ((TextViewHolder) holder);
            TextView tvTitle = textViewHolder.mTvTitle;
            TextView tvAuthor = textViewHolder.mTvAuthor;
            TextView tvPubDate = textViewHolder.mTvPubDate;

            tvTitle.setText(content.desc);
            tvAuthor.setText(content.who);
            tvPubDate.setText(content.publishedAt);

            textViewHolder.mRelativeLayout.setOnClickListener((listener) -> {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(WebViewActivity.TITLE, content.desc);
                intent.putExtra(WebViewActivity.URL, content.url);
                mContext.startActivity(intent);
            });
        }

        if (holder instanceof ImageViewHolder) {
            ImageViewHolder imageViewHolder = ((ImageViewHolder) holder);
            ViewPager vpImage = imageViewHolder.mVpImage;
            RelativeLayout relativeLayout = imageViewHolder.mRelativeLayout;
            TextView tvAuthor = imageViewHolder.mTvAuthor;
            TextView tvPubDate = imageViewHolder.mTvPubDate;
            TextView tvTitle = imageViewHolder.mTvTitle;

            tvTitle.setText(content.desc);
            tvAuthor.setText(content.who);
            tvPubDate.setText(content.publishedAt);

            ImageViewPagerAdapter pagerAdapter = new ImageViewPagerAdapter(mContext);
            pagerAdapter.setResources(content.images);
            vpImage.setAdapter(pagerAdapter);

            relativeLayout.setOnClickListener((listener) -> {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(WebViewActivity.TITLE, content.desc);
                intent.putExtra(WebViewActivity.URL, content.url);
                mContext.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    @Override
    public int getItemViewType(int position) {
        Content content = mContents.get(position);
        List<String> images = content.images;
        return images.size() == 0 ? ITEM_TYPE.VIEW_TYPE_TEXT.ordinal() : ITEM_TYPE.VIEW_TYPE_IMAGE.ordinal();
    }
}
