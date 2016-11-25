package com.zhaochunqi.android.gankio.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private static final int VIEW_TYPE_IMAGE = 0;
    private static final int VIEW_TYPE_TEXT = 1;

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

        private final ImageView mImageDisplay;
        private final TextView mTvTitle;
        private final TextView mTvAuthor;
        private final TextView mTvPubDate;
        private final RelativeLayout mRelativeLayout;

        public ImageViewHolder(View itemView) {
            super(itemView);

            mImageDisplay = (ImageView) itemView.findViewById(R.id.image_display);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvAuthor = ((TextView) itemView.findViewById(R.id.tv_author));
            mTvPubDate = ((TextView) itemView.findViewById(R.id.tv_pub_date));
            mRelativeLayout = ((RelativeLayout) itemView.findViewById(R.id.holder));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case VIEW_TYPE_TEXT:
                View textContentView = inflater.inflate(R.layout.item_text, parent, false);
                viewHolder = new TextViewHolder(textContentView);
                break;
            case VIEW_TYPE_IMAGE:
                View imageContentView = inflater.inflate(R.layout.item_pager_image, parent, false);
                viewHolder = new ImageViewHolder(imageContentView);
                break;
            default:
                View view = inflater.inflate(R.layout.item_text, parent, false);
                viewHolder = new TextViewHolder(view);
                break;
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
            ImageView imageDisplay = imageViewHolder.mImageDisplay;
            RelativeLayout relativeLayout = imageViewHolder.mRelativeLayout;
            TextView tvAuthor = imageViewHolder.mTvAuthor;
            TextView tvPubDate = imageViewHolder.mTvPubDate;
            TextView tvTitle = imageViewHolder.mTvTitle;

            tvTitle.setText(content.desc);
            tvAuthor.setText(content.who);
            tvPubDate.setText(content.publishedAt);

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
        if (images.size() == 0) {
            return VIEW_TYPE_TEXT;
        }
        return VIEW_TYPE_IMAGE;
    }
}
