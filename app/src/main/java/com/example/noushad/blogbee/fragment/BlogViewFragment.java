package com.example.noushad.blogbee.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noushad.blogbee.Interface.ApiInterface;
import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.Retrofit.ServiceGenerator;
import com.example.noushad.blogbee.adapter.CommentsAdapter;
import com.example.noushad.blogbee.model.CommentSuccessResponse;
import com.example.noushad.blogbee.model.singlePostResponse.CommentsItem;
import com.example.noushad.blogbee.model.singlePostResponse.PostDetails;
import com.example.noushad.blogbee.utils.SharedPrefManager;
import com.example.noushad.blogbee.utils.WebOperations;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
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
    private PostDetails mPostResponse;
    private ProgressDialog progressDialog;
    private CircleImageView profileImage;
    private ImageButton reportButton;

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

                floatingActionButton.setImageResource(R.drawable.ic_favorite_enabled);
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
        progressDialog = new ProgressDialog(getActivity());
        profileImage = (CircleImageView) view.findViewById(R.id.creatorProfileImage);
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
        reportButton = (ImageButton) view.findViewById(R.id.reportButton);

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportButton.setImageResource(R.drawable.ic_post_reported);
            }
        });

    }

    private void postComment(String text) {

        progressDialog.setMessage("Getting User Information...");
        progressDialog.show();
        Call<CommentSuccessResponse> responseCall = mService.postComment(mPostResponse.getId(), SharedPrefManager.getInstance(getActivity()).getAuthToken(), text);
        responseCall.enqueue(new Callback<CommentSuccessResponse>() {
            @Override
            public void onResponse(Call<CommentSuccessResponse> call, Response<CommentSuccessResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    mCommentBox.setText("");
                    getPostFromServer(mPostId, UPDATE_COMMENTS);

                } else {
                    Toast.makeText(getActivity(), "Error Posting Comment", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommentSuccessResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static final String TAG = "BlogViewFragment";

    private void getPostFromServer(int id, final int command) {
        setLayoutVisibility(View.INVISIBLE);
        Call<PostDetails> singlePostResponse = mService.getSpecifiedPost(id);

        singlePostResponse.enqueue(new Callback<PostDetails>() {
            @Override
            public void onResponse(Call<PostDetails> call, Response<PostDetails> response) {
                mProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    try {

                        mPostResponse = response.body();
                       // Log.d(TAG, "onResponse: " + response.body().toString());
                        setLayoutVisibility(View.VISIBLE);
                        if (command == UPDATE_COMMENTS)
                            setUpCommentsList(mPostResponse.getComments());
                        else
                            updateUI();

                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: "+e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<PostDetails> call, Throwable t) {
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
        try {
            mTotalCommentsTextView.setText(String.valueOf(mPostResponse.getComments().size()));
            WebOperations.loadImage(getActivity(), mCoverImageView, mPostResponse.getMediumCover());
            if (WebOperations.hasValidPath(mPostResponse.getCreatorInfo().getSmallCover()))
                WebOperations.loadImage(getActivity(), profileImage, mPostResponse.getCreatorInfo().getSmallCover());
            mNameTextView.setText(mPostResponse.getCreatorInfo().getName());
            mLastUpdateTextView.setText(mPostResponse.getLastChange());
            mTitleTextView.setText(mPostResponse.getTitle());
            mBlogDescription.setText(mPostResponse.getDetails());
            setUpCommentsList(mPostResponse.getComments());

        } catch (Exception e) {
            setLayoutVisibility(View.GONE);
           // Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d(TAG, "updateUI: "+e.getMessage());
        }

    }


    private void setUpCommentsList(List<CommentsItem> comments) {
        CommentsAdapter commentsAdapter = new CommentsAdapter((AppCompatActivity) getActivity(), comments);
        ExpandableHeightListView expandableListView = (ExpandableHeightListView) mView.findViewById(R.id.comments_list);
        expandableListView.setAdapter(commentsAdapter);
        expandableListView.setExpanded(true);
    }

}
