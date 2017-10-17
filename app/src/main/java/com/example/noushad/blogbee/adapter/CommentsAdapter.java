package com.example.noushad.blogbee.adapter;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.model.singlePostResponse.CommentsItem;

import java.util.List;

/**
 * Created by noushad on 7/19/17.
 */

public class CommentsAdapter extends ArrayAdapter<CommentsItem> {

    List<CommentsItem> mCommentsList;

    public CommentsAdapter(AppCompatActivity activity, List<CommentsItem> comments) {
        super(activity,0,comments);
        mCommentsList = comments;

    }

    @Override
    public int getCount() {
        return mCommentsList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.comment_item_layout, parent, false);
        }

        final CommentsItem currentComment = getItem(position);

        TextView nameTV = (TextView) listItemView.findViewById(R.id.comment_profile_name);
        nameTV.setText(currentComment.getCreatorInfo().getName());

        TextView commentTv = (TextView) listItemView.findViewById(R.id.comment_tv);
        commentTv.setText(currentComment.getDetails());

        ImageView commentImage = (ImageView) listItemView.findViewById(R.id.comment_profile_image);
        Glide.with(commentImage.getContext())
                .load(currentComment.getCreatorInfo().getCoverPhoto())
                .fitCenter()
                .into(commentImage);
        return listItemView;

    }

}
