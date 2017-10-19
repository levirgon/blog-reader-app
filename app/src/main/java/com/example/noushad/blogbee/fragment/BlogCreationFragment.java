package com.example.noushad.blogbee.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.utils.ImageUtils;
import com.example.noushad.blogbee.utils.SharedPrefManager;
import com.example.noushad.blogbee.utils.WebOperations;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by noushad on 7/17/17.
 */

public class BlogCreationFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    public static final String BLOG_CREATION_FRAGMENT = "blog_creation_fragment";
    private ImageView coverImage;
    private EditText titleText;
    private EditText descriptionEditText;
    private String mFilepath;
    private ImageButton mCoverUploadButton;
    private Button postButton;
    private TextView mSelectedCategoriesText;
    private ImageButton mCategoriesButton;
    private String[] categoriesList = {"Economics", "Computer Science", "Career", "Political", "Finance", "Gaming", "Celebrity Gossip",
            "Sports", "Entertainment", "Lifestyle", "Science", "Education", "Fitness", "Technology", "Social Media", "Business"};
    private boolean[] checkedItems;
    private ArrayList<Integer> selectedCategories;
    private File mFile;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Create Blog");
        View view = inflater.inflate(R.layout.create_blog, container, false);
        initializeViews(view);
        setButtonListeners();
        return view;
    }

    private void setButtonListeners() {
        mCoverUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
        mCategoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryDialog();
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!faultsFound()) {
                    WebOperations.createPost(getActivity(), SharedPrefManager.getInstance(getActivity())
                            .getAuthToken(), titleText.getText().toString(), descriptionEditText.getText().toString(), mFile);


                }
            }
        });
    }


    private void showCategoryDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setTitle("Categories");
        mBuilder.setMultiChoiceItems(categoriesList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int itemIndex, boolean isChecked) {
                if (isChecked) {
                    selectedCategories.add(itemIndex);
                } else {
                    selectedCategories.remove(selectedCategories.indexOf(itemIndex));
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String items = "";
                for (int i = 0; i < selectedCategories.size(); i++) {
                    items += categoriesList[selectedCategories.get(i)];
                    if (i != selectedCategories.size() - 1) {
                        items += ", ";
                    }
                }
                mSelectedCategoriesText.setText(items);
            }
        });
        mBuilder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mBuilder.setNeutralButton("CLEAR ALL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cleanCategories();
            }
        });

        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
    }


    private void cleanCategories() {
        for (int i = 0; i < checkedItems.length; i++)
            checkedItems[i] = false;
        selectedCategories.clear();
        mSelectedCategoriesText.setText("");
    }


    private void initializeViews(View view) {
        coverImage = (ImageView) view.findViewById(R.id.blogCoverImage);
        mCoverUploadButton = (ImageButton) view.findViewById(R.id.imageUploadButton);
        mCategoriesButton = (ImageButton) view.findViewById(R.id.ib_categories);
        mSelectedCategoriesText = (TextView) view.findViewById(R.id.tv_selected_items);
        titleText = (EditText) view.findViewById(R.id.titleEditText);
        descriptionEditText = (EditText) view.findViewById(R.id.descriptionEditText);
        postButton = (Button) view.findViewById(R.id.postButton);
        checkedItems = new boolean[categoriesList.length];
        selectedCategories = new ArrayList<>();
    }

    private boolean faultsFound() {
        int postLength = descriptionEditText.getText().toString().trim().length();
        if (TextUtils.isEmpty(titleText.getText().toString())) {
            Toast.makeText(getActivity(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(descriptionEditText.getText().toString())) {
            Toast.makeText(getActivity(), "Description cannot be empty", Toast.LENGTH_SHORT).show();
            return true;
        } else if ( postLength < 60) {
            Toast.makeText(getActivity(), "Description must contain 60 characters or more,\n Your Post Currently Contains : "+postLength+" words.", Toast.LENGTH_SHORT).show();
            return true;

        } else if ((coverImage.getDrawable() == null)) {
            Toast.makeText(getActivity(), "Cover Image cannot be empty", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void pickImage() {
        Intent chooserIntent = ImageUtils.getChooserIntent();
        startActivityForResult(chooserIntent, PICK_IMAGE);

    }


    private static final String TAG = "BlogCreationFragment";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {

            mFile = ImageUtils.getImageFile(getContext(), data.getData(), coverImage);

        }

    }


}

