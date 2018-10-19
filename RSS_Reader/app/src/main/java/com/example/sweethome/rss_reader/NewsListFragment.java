package com.example.sweethome.rss_reader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class NewsListFragment extends Fragment {
    private RecyclerView news_recycler;
    private List<RssNewsModel> rssNewsModels=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_news_list,container,false);
        news_recycler=rootview.findViewById(R.id.news_recycler_view);
        for(int i=0;i<20;i++){
            rssNewsModels.add(new RssNewsModel("Title #"+i,"Body"));
        }
        news_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        RssNewsListAdapter rssNewsListAdapter=new RssNewsListAdapter(rssNewsModels);
        news_recycler.setAdapter(rssNewsListAdapter);

        return rootview;
    }
}
