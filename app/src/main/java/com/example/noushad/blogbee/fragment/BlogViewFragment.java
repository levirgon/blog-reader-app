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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noushad.blogbee.Interface.ApiInterface;
import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.Retrofit.ServiceGenerator;
import com.example.noushad.blogbee.adapter.CommentsAdapter;
import com.example.noushad.blogbee.model.CommentSuccessResponse;
import com.example.noushad.blogbee.model.singlePostResponseModel.CommentsItem;
import com.example.noushad.blogbee.model.singlePostResponseModel.SinglePostResponse;
import com.example.noushad.blogbee.utils.SharedPrefManager;
import com.example.noushad.blogbee.utils.WebOperations;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by noushad on 7/17/17.
 */


public class BlogViewFragment extends Fragment {

    private static final String ARG_POST_ID = "post_id";
    private static final int UPDATE_ALL = 1;
    private static final int UPDATE_COMMENTS = 2;

    private ImageView mCoverImageView;
    private TextView mNameTextView;
    private TextView mLastUpdateTextView;
    private TextView mTitleTextView;
    private TextView mBlogDescription;
    private TextView mTotalCommentsTextView;
    private View mView;
    private int mPostId;
    private ProgressBar mProgressBar;
    private Button mCommentButton;
    private EditText mCommentBox;
    private ApiInterface mService;
    private SinglePostResponse mPostResponse;

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
        mService = ServiceGenerator.createService(ApiInterface.class);
        initializeViews(view);
        final FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                floatingActionButton.setImageResource(R.drawable.ic_fvourite_added);
                Snackbar snackbar = Snackbar.make(view, "Blog Bookmarked", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        floatingActionButton.setImageResource(R.drawable.ic_favourite_disabled);
                        Snackbar bookmarkSnack = Snackbar.make(view, "Removed From Bookmarks!", Snackbar.LENGTH_SHORT);
                        bookmarkSnack.show();
                    }
                });
                ;
                snackbar.show();
            }
        });
        mService = ServiceGenerator.createService(ApiInterface.class);
        mPostId = (int) getArguments().getSerializable(ARG_POST_ID);
        getPostFromServer(mPostId, UPDATE_ALL);
        return view;
    }

    private void initializeViews(View view) {
        getActivity().setTitle("Blog View");
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Blog View");
        mProgressBar = (ProgressBar) view.findViewById(R.id.blog_loading_progress);
        mCoverImageView = (ImageView) view.findViewById(R.id.blog_cover);
        mNameTextView = (TextView) view.findViewById(R.id.name_text_full);
        mLastUpdateTextView = (TextView) view.findViewById(R.id.last_update_full);
        mTitleTextView = (TextView) view.findViewById(R.id.blog_title_full);
        mBlogDescription = (TextView) view.findViewById(R.id.blog_description);
        mTotalCommentsTextView = (TextView) view.findViewById(R.id.total_comments_full);
        mCommentButton = (Button) view.findViewById(R.id.comment_button);
        mCommentBox = (EditText) view.findViewById(R.id.comment_box);
        mCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mCommentBox.getText().toString();
                if (!text.isEmpty()) {
                    postComment(text);
                } else {
                    Toast.makeText(getActivity(), "Cannot post empty comment", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void postComment(String text) {

        Call<CommentSuccessResponse> responseCall = mService.postComment(mPostResponse.getId(), SharedPrefManager.getInstance(getActivity()).getAuthToken(), text);
        responseCall.enqueue(new Callback<CommentSuccessResponse>() {
            @Override
            public void onResponse(Call<CommentSuccessResponse> call, Response<CommentSuccessResponse> response) {
                if (response.isSuccessful()) {
                    mCommentBox.setText("");
                    getPostFromServer(mPostId, UPDATE_COMMENTS);

                } else {
                    Toast.makeText(getActivity(), "Error Posting Comment", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommentSuccessResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPostFromServer(int id, final int command) {
        setLayoutVisibility(View.INVISIBLE);
        Call<SinglePostResponse> singlePostResponse = mService.getSpecifiedPost(id);

        singlePostResponse.enqueue(new Callback<SinglePostResponse>() {
            @Override
            public void onResponse(Call<SinglePostResponse> call, Response<SinglePostResponse> response) {
                mProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    try {
                        mPostResponse = response.body();
                        setLayoutVisibility(View.VISIBLE);
                        if (command == UPDATE_COMMENTS)
                            setUpCommentsList(mPostResponse.getComments());
                        else
                            updateUI();

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SinglePostResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setLayoutVisibility(int command) {

        mTotalCommentsTextView.setVisibility(command);
        mNameTextView.setVisibility(command);
        mLastUpdateTextView.setVisibility(command);
        mTitleTextView.setVisibility(command);
        mBlogDescription.setVisibility(command);
        mCoverImageView.setVisibility(command);
    }

    private void updateUI() {
        Toast.makeText(getActivity(), "UI refreshed", Toast.LENGTH_LONG).show();
        try {
            mTotalCommentsTextView.setText(String.valueOf(mPostResponse.getComments().size()));
            WebOperations.loadImage(getActivity(), mCoverImageView, mPostResponse.getCoverPhoto());
            mNameTextView.setText(mPostResponse.getCreatorInfo().getName());
            mLastUpdateTextView.setText(mPostResponse.getLastChange());
            mTitleTextView.setText(mPostResponse.getTitle());
            mBlogDescription.setText(mPostResponse.getDetails());
            setUpCommentsList(mPostResponse.getComments());

        } catch (Exception e) {
            setLayoutVisibility(View.GONE);
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    private void setUpCommentsList(List<CommentsItem> comments) {
        Toast.makeText(getActivity(),"comments refreshed!", Toast.LENGTH_LONG).show();
        CommentsAdapter commentsAdapter = new CommentsAdapter((AppCompatActivity) getActivity(), comments);
        ExpandableHeightListView expandableListView = (ExpandableHeightListView) mView.findViewById(R.id.comments_list);
        expandableListView.setAdapter(commentsAdapter);
        expandableListView.setExpanded(true);
    }

}
