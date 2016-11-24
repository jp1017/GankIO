package com.zhaochunqi.android.gankio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhaochunqi.android.gankio.beans.Content;

import java.util.List;

/**
 * Created by developer on 25/11/2016.
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
    private List<Content> mContents;
    private Context mContext;

    public ContentAdapter(List<Content> contents) {
        mContents = contents;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvTitle;
        private final TextView mTvAuthor;
        private final TextView mTvPubDate;
        private final RelativeLayout mRelativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            mTvTitle = ((TextView) itemView.findViewById(R.id.tv_title));
            mTvAuthor = ((TextView) itemView.findViewById(R.id.tv_author));
            mTvPubDate = ((TextView) itemView.findViewById(R.id.tv_pub_date));
            mRelativeLayout = ((RelativeLayout) itemView.findViewById(R.id.holder));
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View contactView = inflater.inflate(R.layout.item_text, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Content content = mContents.get(position);

        TextView tvTitle = holder.mTvTitle;
        TextView tvAuthor = holder.mTvAuthor;
        TextView tvPubDate = holder.mTvPubDate;

        tvTitle.setText(content.desc);
        tvAuthor.setText(content.who);
        tvPubDate.setText(content.publishedAt);

        holder.mRelativeLayout.setOnClickListener((listener) -> {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(WebViewActivity.TITLE, content.desc);
            intent.putExtra(WebViewActivity.URL, content.url);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }
}
