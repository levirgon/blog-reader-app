package com.example.noushad.blogbee.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.view.ListFragment;

/**
 * Created by noushad on 7/16/17.
 */

public class MyAdapter extends RecyclerView.Adapter {

    private ListFragment.OnItemSelectedInterface mListener;

    public MyAdapter() {

    }

    public MyAdapter(ListFragment.OnItemSelectedInterface listener) {

        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((MyViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mCoverImage;
        private ImageView mProfileImage;
        private TextView mNameTextView;
        private TextView mCommentTextView;
        private TextView mTimeTextView;
        private int mIndex;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCommentTextView = (TextView) itemView.findViewById(R.id.total_comments);
            mCoverImage = (ImageView) itemView.findViewById(R.id.cover_image);
            mProfileImage = (ImageView) itemView.findViewById(R.id.blog_profile_image);
            mNameTextView = (TextView) itemView.findViewById(R.id.name_text);
            mTimeTextView = (TextView) itemView.findViewById(R.id.last_update);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onListBlogSelected(mIndex);

        }

        public void bindView(int position) {
            //this is where the resources for all views should be set.
            mIndex = position;
            mCoverImage.setImageResource(R.drawable.house);

            Glide.with(mProfileImage.getContext())
                    .load(R.drawable.profile_image)
                    .fitCenter()
                    .into(mProfileImage);

            mNameTextView.setText("Noushad Hasan");
            mCommentTextView.setText("Total comments : " + "21");
            mTimeTextView.setText("10 minutes ago.");
        }
    }
}
