package com.example.noushad.blogbee.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.noushad.blogbee.Interface.ApiInterface;
import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.Retrofit.ServiceGenerator;
import com.example.noushad.blogbee.adapter.BlogRecycleAdapter;
import com.example.noushad.blogbee.model.allPostsResponseModel.AllpostsResponse;
import com.example.noushad.blogbee.model.allPostsResponseModel.DataItem;
import com.example.noushad.blogbee.utils.PaginationAdapterCallback;
import com.example.noushad.blogbee.utils.PaginationScrollListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by noushad on 7/17/17.
 */

public class ListFragment extends Fragment implements PaginationAdapterCallback {


    OnItemSelectedInterface mListener;
    RecyclerView mRecyclerView;
    private ApiInterface mService;
    private BlogRecycleAdapter mAdapter;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 20; // just a initial dummy value
    private int mCurrentPage = 1;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    LinearLayout errorLayout;
    Button btnRetry;
    private TextView txtError;
    private PaginationAdapterCallback mCallback;

    @Override
    public void retryPageLoad() {
        loadNextPage();
    }

    public interface OnItemSelectedInterface {
        void onListBlogSelected(int index);
    }


    public ListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("News Feed");
        View view = initializeViews(inflater, container);
        return view;
    }

    @NonNull
    private View initializeViews(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mService = ServiceGenerator.createService(ApiInterface.class);
        mListener = (OnItemSelectedInterface) getActivity();
        mCallback = (PaginationAdapterCallback) getActivity();
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        progressBar = (ProgressBar) view.findViewById(R.id.main_progress);
        errorLayout = (LinearLayout) view.findViewById(R.id.error_layout);
        btnRetry = (Button) view.findViewById(R.id.error_btn_retry);
        txtError = (TextView) view.findViewById(R.id.error_txt_cause);
        return view;
    }

    private void loadFirstPage() {
        hideErrorView();

        callResponse().enqueue(new Callback<AllpostsResponse>() {
            @Override
            public void onResponse(Call<AllpostsResponse> call, Response<AllpostsResponse> response) {
                if (response.isSuccessful()) {
                    List<DataItem> dataItems = (response.body()).getData();
                    TOTAL_PAGES = response.body().getMeta().getPagination().getTotalPages();
                    progressBar.setVisibility(View.GONE);
                    mAdapter.addAll(dataItems);
                    if (mCurrentPage != TOTAL_PAGES) mAdapter.addLoadingFooter();
                    else isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<AllpostsResponse> call, Throwable t) {
                showErrorView(t);
            }
        });


    }

    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void showErrorView(Throwable throwable) {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            txtError.setText(throwable.getMessage());
        }
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new BlogRecycleAdapter(getActivity().getApplicationContext(), mListener, mCallback);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mRecyclerView.setAdapter(mAdapter);
        }

        loadFirstPage();

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFirstPage();
            }
        });

        mRecyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                mCurrentPage++;
                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });


    }

    private void loadNextPage() {

        callResponse().enqueue(new Callback<AllpostsResponse>() {
            @Override
            public void onResponse(Call<AllpostsResponse> call, Response<AllpostsResponse> response) {
                mAdapter.removeLoadingFooter();
                if (response.isSuccessful()) {
                    isLoading = false;
                    List<DataItem> results = response.body().getData();
                    mAdapter.addAll(results);
                    if (mCurrentPage != TOTAL_PAGES) mAdapter.addLoadingFooter();
                    else isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<AllpostsResponse> call, Throwable t) {
                mAdapter.showRetry(true, t.getMessage());
            }
        });
    }

    private Call<AllpostsResponse> callResponse() {
        return mService.getPostsByPage(mCurrentPage);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

}
