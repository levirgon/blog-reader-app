package com.example.noushad.blogbee.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.fragment.ListFragment;
import com.example.noushad.blogbee.model.allPostsResponseModel.DataItem;

import java.util.ArrayList;

/**
 * Created by noushad on 7/16/17.
 */

public class MyRecycleAdapter extends RecyclerView.Adapter {

    private final ArrayList<DataItem> mItems;
    private ListFragment.OnItemSelectedInterface mListener;
    private Context mContext;


    public MyRecycleAdapter(Context context, ListFragment.OnItemSelectedInterface listener, ArrayList<DataItem> dataItems) {

        mContext = context;
        mItems = dataItems;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //((MyViewHolder) holder).bindView(position);
        DataItem dataItem = mItems.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }


    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mCoverImage;
        private ImageView mProfileImage;
        private TextView mLastUpdatedTextView;
        private TextView mCommentCountTextView;
        private TextView mNameTextView;
        private TextView mTitleTextView;
        private int mIndex;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCommentCountTextView = (TextView) itemView.findViewById(R.id.total_comments);
            mCoverImage = (ImageView) itemView.findViewById(R.id.cover_image);
            mProfileImage = (ImageView) itemView.findViewById(R.id.blog_profile_image);
            mNameTextView = (TextView) itemView.findViewById(R.id.name_text);
            mLastUpdatedTextView = (TextView) itemView.findViewById(R.id.last_update);
            mTitleTextView = (TextView) itemView.findViewById(R.id.blog_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onListBlogSelected(mIndex);

        }


        private DataItem mDataItem;

        public void bind(DataItem dataItem) {
            mDataItem = dataItem;
            mLastUpdatedTextView.setText(mDataItem.getLastChange());

            LoadImageFromWebOperations(mCoverImage, mDataItem.getCoverPhoto());
            LoadImageFromWebOperations(mProfileImage, (mDataItem.getCreatorInfo()).getCoverPhoto());


            mTitleTextView.setText(mDataItem.getTitle());
            mCommentCountTextView.setText(String.valueOf("Total Comments : " + mDataItem.getCommentCount()));
            mNameTextView.setText((mDataItem.getCreatorInfo()).getName());




        }
    }

    public void LoadImageFromWebOperations(final ImageView imageView, final String url) {

        Glide.with(mContext).load(url).into(imageView);
    }
}
