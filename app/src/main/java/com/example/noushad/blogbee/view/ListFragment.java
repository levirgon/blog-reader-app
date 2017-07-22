package com.example.noushad.blogbee.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.noushad.blogbee.controller.MyAdapter;
import com.example.noushad.blogbee.R;

/**
 * Created by noushad on 7/17/17.
 */

public class ListFragment extends Fragment {



    public interface OnItemSelectedInterface {

        void onListBlogSelected(int index);
    }


    public ListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Blogs Feed");

        OnItemSelectedInterface listener = (OnItemSelectedInterface) getActivity();
        View view = inflater.inflate(R.layout.fragments_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        MyAdapter myAdapter = new MyAdapter(listener);
        recyclerView.setAdapter(myAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }
}
