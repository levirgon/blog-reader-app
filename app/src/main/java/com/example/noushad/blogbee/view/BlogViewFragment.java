package com.example.noushad.blogbee.view;

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
import android.widget.ListView;

import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.controller.CommentsAdapter;
import com.example.noushad.blogbee.model.Comments;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by noushad on 7/17/17.
 */


public class BlogViewFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.blog_view, container, false);

        getActivity().setTitle("Blog View");

        List<Comments> comments = new ArrayList<>();
        comments.add(new Comments("Noushad", "Woow so beautiful"));
        comments.add(new Comments("Dhrubo", "Woow so beautiful"));
        comments.add(new Comments("Arnob", "Woow so beautiful"));
        comments.add(new Comments("Kabbo", "Woow so beautiful"));
        comments.add(new Comments("Anupam", "Woow so beautiful"));
        comments.add(new Comments("Noushad", "Woow so beautiful"));
        comments.add(new Comments("Noushad", "Woow so beautiful"));
        comments.add(new Comments("Noushad", "Woow so beautiful"));
        comments.add(new Comments("Noushad", "Woow so beautiful"));
        comments.add(new Comments("Noushad", "Woow so beautiful"));
        comments.add(new Comments("Noushad", "Woow so beautiful"));
        comments.add(new Comments("Noushad", "Woow so beautiful"));
        comments.add(new Comments("Noushad", "Woow so beautiful"));
        comments.add(new Comments("Noushad", "Woow so beautiful"));
        comments.add(new Comments("Noushad", "Woow so beautiful"));
        comments.add(new Comments("Noushad", "Woow so beautiful"));
        comments.add(new Comments("Noushad", "Woow so beautiful"));
        comments.add(new Comments("Noushad", "Woow so beautiful"));
        comments.add(new Comments("Noushad", "Woow so beautiful"));

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Blog View");

        CommentsAdapter commentsAdapter = new CommentsAdapter((AppCompatActivity) getActivity(), comments);

        ListView listView = (ListView) view.findViewById(R.id.comments_list);

        listView.setAdapter(commentsAdapter);

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
                });;
                snackbar.show();
            }
        });

        return view;
    }
}
