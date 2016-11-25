package com.zhaochunqi.android.gankio.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
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
        private final FrameLayout mFrameLayout;
        private final ViewPager mVpImage;
        private final LinearLayout mLinearLayout;

        public ImageViewHolder(View itemView) {
            super(itemView);

            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvAuthor = ((TextView) itemView.findViewById(R.id.tv_author));
            mTvPubDate = ((TextView) itemView.findViewById(R.id.tv_pub_date));
            mFrameLayout = ((FrameLayout) itemView.findViewById(R.id.holder));
            mVpImage = (ViewPager) itemView.findViewById(R.id.vp_image);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.cycle_indicator);
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
                startWebViewActivity(content);
            });
        }

        if (holder instanceof ImageViewHolder) {
            ImageViewHolder imageViewHolder = ((ImageViewHolder) holder);
            ViewPager vpImage = imageViewHolder.mVpImage;
            FrameLayout frameLayout = imageViewHolder.mFrameLayout;
            TextView tvAuthor = imageViewHolder.mTvAuthor;
            TextView tvPubDate = imageViewHolder.mTvPubDate;
            TextView tvTitle = imageViewHolder.mTvTitle;
            LinearLayout linearLayout = imageViewHolder.mLinearLayout;

            tvTitle.setText(content.desc);
            tvAuthor.setText(content.who);
            tvPubDate.setText(content.publishedAt);

            ImageViewPagerAdapter pagerAdapter = new ImageViewPagerAdapter(mContext);
            pagerAdapter.setResources(content.images);
            pagerAdapter.setListener(() -> startWebViewActivity(content));

            linearLayout.removeAllViews();
            ImageView[] mIndicators = new ImageView[content.images.size()];
            for (int i = 0; i < mIndicators.length; i++) {
                mIndicators[i] = new ImageView(mContext);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10, 0, 10, 0);
                mIndicators[i].setLayoutParams(lp);
                linearLayout.addView(mIndicators[i]);
            }

            setIndicator(0, mIndicators);
            vpImage.setAdapter(pagerAdapter);
        }
    }

    private void startWebViewActivity(Content content) {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra(WebViewActivity.TITLE, content.desc);
        intent.putExtra(WebViewActivity.URL, content.url);
        mContext.startActivity(intent);
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

    private void setIndicator(int selectedPosition, ImageView[] mIndicators) {
        try {

            for (int i = 0; i < mIndicators.length; i++) {
                mIndicators[i]
                        .setBackgroundResource(R.drawable.dot_unselected);
            }
            if (mIndicators.length > selectedPosition)
                mIndicators[selectedPosition]
                        .setBackgroundResource(R.drawable.dot_selected);
        } catch (Exception e) {
            Logger.d("指示器路径不正确");
        }
    }

}
