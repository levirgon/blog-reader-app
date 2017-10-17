package com.example.noushad.blogbee.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.noushad.blogbee.utils.SharedPrefManager;
import com.example.noushad.blogbee.utils.WebOperations;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by noushad on 7/17/17.
 */

public class BlogCreationFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    public static final String BLOG_CREATION_FRAGMENT = "blog_creation_fragment";
    private ImageView coverImage;
    private EditText titleText;
    private EditText descriptionEditText;
    private String mFilePath;
    private ImageButton mCoverUploadButton;
    private Button postButton;
    private TextView mSelectedCategoriesText;
    private ImageButton mCategoriesButton;
    private String[] categoriesList = {"Economics", "Computer Science", "Career", "Political", "Finance", "Gaming", "Celebrity Gossip",
            "Sports", "Entertainment", "Lifestyle", "Science", "Education", "Fitness", "Technology", "Social Media", "Business"};
    private boolean[] checkedItems;
    private ArrayList<Integer> selectedCategories;


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
                if (isStoragePermissionGranted()) {
                    Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    getIntent.setType("image/*");
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickIntent.setType("image/*");
                    Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
                    startActivityForResult(chooserIntent, PICK_IMAGE);
                }
            }
        });
        mCategoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!faultsFound()) {
                    if (WebOperations.createPost(getActivity(), SharedPrefManager.getInstance(getActivity())
                            .getAuthToken(), titleText.getText().toString(), descriptionEditText.getText().toString(), mFilePath)) {
                    cleanViews();
                    }
                }
            }
        });
    }

    private void cleanCategories() {
        for (int i = 0; i < checkedItems.length; i++)
            checkedItems[i] = false;
        selectedCategories.clear();
        mSelectedCategoriesText.setText("");
    }

    private void cleanViews() {
        coverImage.setImageDrawable(null);
        cleanCategories();
        titleText.setText("");
        descriptionEditText.setText("");
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
        if (TextUtils.isEmpty(titleText.getText().toString())) {
            Toast.makeText(getActivity(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(descriptionEditText.getText().toString())) {
            Toast.makeText(getActivity(), "Description cannot be empty", Toast.LENGTH_SHORT).show();
            return true;
        } else if ((coverImage.getDrawable() == null)) {
            Toast.makeText(getActivity(), "Cover Image cannot be empty", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    //code for getting image from storage...
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    private static final String TAG = "BlogCreationFragment";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            mFilePath = getRealPathFromURI(imageUri);
            coverImage.setImageURI(imageUri);

        }

    }

    private String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (requestCode == PICK_IMAGE) {
            Uri imageUri = intent.getData();
        }
    }
    //code for getting image from storage...


}
