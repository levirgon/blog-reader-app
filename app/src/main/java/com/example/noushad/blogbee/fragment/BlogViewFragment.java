package com.example.noushad.blogbee.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.noushad.blogbee.Interface.ApiInterface;
import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.Retrofit.ServiceGenerator;
import com.example.noushad.blogbee.adapter.CommentsAdapter;
import com.example.noushad.blogbee.model.singlePostResponseModel.CommentsItem;
import com.example.noushad.blogbee.model.singlePostResponseModel.SinglePostResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by noushad on 7/17/17.
 */


public class BlogViewFragment extends Fragment {

    private static final String ARG_POST_ID = "post_id";

    private ApiInterface mService;
    private ImageView mCoverImageView;
    private TextView mNameTextView;
    private TextView mLastUpdateTextView;
    private TextView mTitleTextView;
    private TextView mBlogDescription;
    private TextView mTotalCommentsTextView;
    private View mView;
    private int mPostId;

    public static BlogViewFragment newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_POST_ID, index);
        BlogViewFragment BlogViewFragment = new BlogViewFragment();
        BlogViewFragment.setArguments(bundle);
        return BlogViewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.blog_view, container, false);

        mView = view;
        getActivity().setTitle("Blog View");
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Blog View");


        mCoverImageView = (ImageView) view.findViewById(R.id.app_bar_image);
        mNameTextView = (TextView) view.findViewById(R.id.name_text_full);
        mLastUpdateTextView = (TextView) view.findViewById(R.id.last_update_full);
        mTitleTextView = (TextView) view.findViewById(R.id.blog_title_full);
        mBlogDescription = (TextView) view.findViewById(R.id.blog_description);
        mTotalCommentsTextView = (TextView) view.findViewById(R.id.total_comments_full);

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar snackbar = Snackbar.make(view, "Blog Bookmarked", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(view, "Removed From Bookmarks!", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });
                ;
                snackbar.show();
            }
        });

        mService = ServiceGenerator.createService(ApiInterface.class);

        mPostId = (int) getArguments().getSerializable(ARG_POST_ID);
        Toast.makeText(getActivity(), String.valueOf(mPostId), Toast.LENGTH_LONG).show();
        getPostFromServer(mPostId, view);


        return view;
    }

    private void getPostFromServer(int id, final View view) {
        Call<SinglePostResponse> singlePostResponse = mService.getSpecifiedPost(id);

        singlePostResponse.enqueue(new Callback<SinglePostResponse>() {
            @Override
            public void onResponse(Call<SinglePostResponse> call, Response<SinglePostResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        SinglePostResponse postResponse = response.body();
                        //Toast.makeText(getActivity(), postResponse.getDetails(), Toast.LENGTH_LONG).show();
                        updateUI(view, postResponse);

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "problem parsing response", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SinglePostResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "failed to get response", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUI(View view, SinglePostResponse postResponse) {

        try {
//           mTotalCommentsTextView.setText((postResponse.getComments()).size());
//           Toast.makeText(getActivity(),"comments size loaded", Toast.LENGTH_LONG).show();

            LoadImageFromWebOperations(mCoverImageView, postResponse.getCoverPhoto());
            mNameTextView.setText(postResponse.getCreatorInfo().getName());
//           Toast.makeText(getActivity(),postResponse.getCreatorInfo().getName(), Toast.LENGTH_LONG).show();

            mLastUpdateTextView.setText(postResponse.getLastChange());
            //         Toast.makeText(getActivity(),"last change loaded", Toast.LENGTH_LONG).show();

            mTitleTextView.setText(postResponse.getTitle());
            //       Toast.makeText(getActivity(),"title loaded", Toast.LENGTH_LONG).show();

            mBlogDescription.setText(postResponse.getDetails());
            //     Toast.makeText(getActivity(),"description loaded", Toast.LENGTH_LONG).show();

            setUpCommentsList(postResponse.getComments(), view);
            //   Toast.makeText(getActivity(),"comments loaded", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "problem here", Toast.LENGTH_LONG).show();
        }

    }

    public void LoadImageFromWebOperations(final ImageView imageView, final String url) {

        Glide.with(getActivity()).load(url).into(imageView);
    }

    private void setUpCommentsList(List<CommentsItem> comments, final View view) {
        CommentsAdapter commentsAdapter = new CommentsAdapter((AppCompatActivity) getActivity(), comments);

        ListView listView = (ListView) view.findViewById(R.id.comments_list);

        listView.setAdapter(commentsAdapter);
    }
}
