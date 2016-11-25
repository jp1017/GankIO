package com.zhaochunqi.android.gankio;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhaochunqi.android.gankio.adapter.ContentAdapter;
import com.zhaochunqi.android.gankio.beans.Content;
import com.zhaochunqi.android.gankio.beans.Datas;
import com.zhaochunqi.android.gankio.network.GankService;
import com.zhaochunqi.android.gankio.network.GankServiceHelper;
import com.zhaochunqi.android.gankio.network.ResponseCodeError;
import com.zhaochunqi.android.gankio.network.WebFailureAction;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {
    private String type;
    public static final String ARG_TYPE = "ARG_TYPE";
    private GankService mGankService;
    private Context mContext;
    private ContentAdapter mContentAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeContainer;
    private int mPage = 1;


    public ContentFragment() {
        // Required empty public constructor
    }


    public static ContentFragment newInstance(String type) {

        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);

        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        type = getArguments().getString(ARG_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_content);
        mRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mSwipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        mContentAdapter = new ContentAdapter(new ArrayList<Content>());
        mContentAdapter.setListener(new ContentAdapter.Listener() {
            @Override
            public void onClick() {
                loadMore();
            }
        });


        mRecyclerView.setAdapter(mContentAdapter);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeContainer.setRefreshing(true);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();
                    }
                }, 2000);
            }
        });

        EndlessRecyclerViewScrollListener listener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                loadMore();
                getContents(page);
            }
        };

        mRecyclerView.addOnScrollListener(listener);

        GankServiceHelper gankServiceHelper = new GankServiceHelper(getActivity().getApplication());
        mGankService = gankServiceHelper.getGankService();
        mContentAdapter.clear();
        getContents(1);
        return view;
    }

    private void refreshData() {
        if (!isNetworkAvailable()) {
            Toast.makeText(mContext, "无网络连接..", Toast.LENGTH_SHORT).show();
            mSwipeContainer.setRefreshing(false);
            return;
        }
        mContentAdapter.clear();
        getContents(1);
    }

    private void getContents(int page) {
        if (!isNetworkAvailable()) {
            Toast.makeText(mContext, "无网络连接..", Toast.LENGTH_SHORT).show();
            mSwipeContainer.setRefreshing(false);
            return;
        }

        mGankService.getDatas(type, "10", String.valueOf(page))
                .compose(RxUtil.normalSchedulers())
                .subscribe(new Action1<Datas>() {
                               @Override
                               public void call(Datas datas) {
                                   if (datas.error) {
                                       throw new ResponseCodeError("error response");
                                   }
                                   List<Content> contents = datas.mContents;
                                   mContentAdapter.addAll(contents);
                                   mContentAdapter.notifyDataSetChanged();

                                   mSwipeContainer.setRefreshing(false);
                               }
                           }
                        , new WebFailureAction());
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void loadMore() {
        getContents(++mPage);
    }
}
