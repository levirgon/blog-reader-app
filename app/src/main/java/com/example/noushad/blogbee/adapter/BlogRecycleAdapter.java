package com.example.noushad.blogbee.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.fragment.ListFragment;
import com.example.noushad.blogbee.model.allPostsResponseModel.DataItem;
import com.example.noushad.blogbee.utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by noushad on 7/16/17.
 */

public class BlogRecycleAdapter extends RecyclerView.Adapter {

    private final List<DataItem> mItems;
    private ListFragment.OnItemSelectedInterface mListener;
    private Context mContext;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private PaginationAdapterCallback mCallback;
    private DataItem mDataItem;


    private String errorMsg;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    public BlogRecycleAdapter(Context context, ListFragment.OnItemSelectedInterface listener,
                              PaginationAdapterCallback callback) {

        mContext = context;
        mItems = new ArrayList<>();
        mListener = listener;
        this.mCallback = callback;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.list_item, parent, false);
                viewHolder = new BlogVH(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DataItem dataItem = mItems.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                BlogVH blogVH = (BlogVH) holder;
                blogVH.bind(dataItem);
                break;
            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;
                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    "An unexpected error occurred!");

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mItems.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }


    private class BlogVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected Context context;
        private ImageView mCoverImage;
        private ImageView mProfileImage;
        private TextView mLastUpdatedTextView;
        private TextView mCommentCountTextView;
        private TextView mNameTextView;
        private TextView mTitleTextView;

        public BlogVH(View itemView) {
            super(itemView);
            mCommentCountTextView = (TextView) itemView.findViewById(R.id.total_comments);
            mCoverImage = (ImageView) itemView.findViewById(R.id.cover_image);
            mProfileImage = (ImageView) itemView.findViewById(R.id.blog_profile_image);
            mNameTextView = (TextView) itemView.findViewById(R.id.name_text);
            mLastUpdatedTextView = (TextView) itemView.findViewById(R.id.last_update);
            mTitleTextView = (TextView) itemView.findViewById(R.id.blog_title);
            itemView.setOnClickListener(this);
        }

        private void bind(DataItem dataItem) {
            mDataItem = dataItem;
            mLastUpdatedTextView.setText(mDataItem.getLastChange());
            LoadImageFromWebOperations(mCoverImage, mDataItem.getCoverPhoto());
            if (mDataItem.hasCreatorInfo()) {
                LoadImageFromWebOperations(mProfileImage, (mDataItem.getUserInfo()).getCoverPhoto());
                mNameTextView.setText((mDataItem.getUserInfo()).getName());
            }
            mTitleTextView.setText(mDataItem.getTitle());
            mCommentCountTextView.setText(String.valueOf("Total Comments : "
                    + mDataItem.getCommentCount()));
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            int id = mItems.get(pos).getId();
            mListener.onListBlogSelected(id);
        }
    }

    private void LoadImageFromWebOperations(final ImageView imageView, final String url) {

        Glide.with(mContext).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter().crossFade().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                imageView.setImageResource(R.drawable.wait);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(imageView);
    }

    private class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        private LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = (ImageButton) itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = (TextView) itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = (LinearLayout) itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();
                    break;
            }
        }
    }


    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(mItems.size() - 1);
        if (errorMsg != null) this.errorMsg = errorMsg;
    }

    public void add(DataItem item) {
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public void addAll(List<DataItem> items) {
        for (DataItem item : items) {
            add(item);
        }
    }

    public void remove(DataItem item) {
        int position = mItems.indexOf(item);
        if (position > -1) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }

    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(mItems.get(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new DataItem());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = mItems.size() - 1;
        DataItem dataItem = mItems.get(position);

        if (dataItem != null) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }
    }
}
