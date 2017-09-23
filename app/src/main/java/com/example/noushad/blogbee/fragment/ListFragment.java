package com.example.noushad.blogbee.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.noushad.blogbee.Interface.ApiInterface;
import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.Retrofit.ServiceGenerator;
import com.example.noushad.blogbee.adapter.MyRecycleAdapter;
import com.example.noushad.blogbee.model.allPostsResponseModel.AllpostsResponse;
import com.example.noushad.blogbee.model.allPostsResponseModel.DataItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by noushad on 7/17/17.
 */

public class ListFragment extends Fragment {


    ArrayList<DataItem> mDataItems;
    OnItemSelectedInterface mListener;
    RecyclerView mRecyclerView;
    private ApiInterface mService;
    private MyRecycleAdapter mAdapter;

    public interface OnItemSelectedInterface {

        void onListBlogSelected(int index);
    }


    public ListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Blogs Feed");

        mService = ServiceGenerator.createService(ApiInterface.class);
        mListener = (OnItemSelectedInterface) getActivity();
        View view = inflater.inflate(R.layout.fragments_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        fetchData();
        return view;
    }

    private void updateUI(List<DataItem> dataItems) {
        mDataItems = new ArrayList<>(dataItems);
        if (mAdapter == null) {
            mAdapter = new MyRecycleAdapter(getActivity(),mListener, mDataItems);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private void fetchData() {

        Call<AllpostsResponse> responseCall = mService.getAllposts();
        responseCall.enqueue(new Callback<AllpostsResponse>() {
            @Override
            public void onResponse(Call<AllpostsResponse> call, Response<AllpostsResponse> response) {
                if (response.isSuccessful()) {
                    List<DataItem> dataItems = (response.body()).getData();
                    updateUI(dataItems);
                }
            }

            @Override
            public void onFailure(Call<AllpostsResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

}
