package com.zhaochunqi.android.gankio;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhaochunqi.android.gankio.beans.Content;
import com.zhaochunqi.android.gankio.beans.Datas;
import com.zhaochunqi.android.gankio.network.GankService;
import com.zhaochunqi.android.gankio.network.GankServiceHelper;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {
    private int mPage;
    public static final String ARG_PAGE = "ARG_PAGE";


    public ContentFragment() {
        // Required empty public constructor
    }


    public static ContentFragment newInstance(int page) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);

        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_content);


        GankService gankService = GankServiceHelper.getGankService();
        gankService.getDatas("Android", "10", "1")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Datas>() {
                    @Override
                    public void call(Datas datas) {
                        if (!datas.error) {
                            List<Content> contents = datas.mContents;
                            ContentAdapter contentAdapter = new ContentAdapter(contents);
                            recyclerView.setAdapter(contentAdapter);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                    layoutManager.getOrientation());
                            recyclerView.addItemDecoration(dividerItemDecoration);

                        } else {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return view;
    }

}
