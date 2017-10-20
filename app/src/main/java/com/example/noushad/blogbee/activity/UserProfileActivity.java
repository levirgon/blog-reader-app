package com.example.noushad.blogbee.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.model.ViewModel.UserViewModel;
import com.example.noushad.blogbee.utils.ImageUtils;
import com.example.noushad.blogbee.utils.SharedPrefManager;
import com.example.noushad.blogbee.utils.WebOperations;
import com.vstechlab.easyfonts.EasyFonts;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    private CircleImageView mProfileImage;
    private TextView mTvName;
    private TextView mTvEmail;
    private TextView mTvPhone;
    private TextView mNameTv;
    private FloatingActionButton reloadButton;
    private File mFile;
    private TextView mTvPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        this.setTitle("Profile");
        initializeViews();

        displayData();
        setUpClickListeners();
    }

    private void setUpClickListeners() {
        mTvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog("name");
            }
        });
        mTvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog("email");
            }
        });
        mTvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog("phone_no");
            }
        });
        mTvPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog("password");
            }
        });
        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();

            }
        });

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadActivity();
            }
        });
    }

    private void reloadActivity() {
        finish();
        startActivity(getIntent());
    }

    private static final int PICK_IMAGE = 1;

    private void showUpdateDialog(final String key) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_info_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText newInfoEdit = (EditText) dialogView.findViewById(R.id.updateInfoEdit);
        TextInputLayout layout = (TextInputLayout) dialogView.findViewById(R.id.updateTextInputLayout);

        layout.setHint(key.toUpperCase());
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = newInfoEdit.getText().toString();
                if (!value.equals(""))
                    WebOperations.updateUserInformation(UserProfileActivity.this, key, value);

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        AlertDialog updateDialog = dialogBuilder.create();
        updateDialog.show();
    }

    private void displayData() {
        UserViewModel user = SharedPrefManager.getInstance(this).getUser();
        mTvName.setText(user.getName());
        mTvName.setTypeface(EasyFonts.caviarDreamsBold(this));
        mTvEmail.setText(user.getEmail());
        mTvEmail.setTypeface(EasyFonts.caviarDreamsBold(this));
        mTvPhone.setText(user.getPhoneNo());
        mTvPhone.setTypeface(EasyFonts.caviarDreamsBold(this));

        String userPictureUrl = user.getCoverPhoto();
        if (WebOperations.hasValidPath(userPictureUrl))
            WebOperations.loadImage(this, mProfileImage, userPictureUrl);
        else {
            Glide.with(this).load(R.drawable.no_profile_image).into(mProfileImage);
        }
        mNameTv.setText(user.getName());
        mNameTv.setTypeface(EasyFonts.caviarDreamsBold(this));
    }

    private void initializeViews() {
        mProfileImage = (CircleImageView) findViewById(R.id.user_profile_image);
        mTvName = (TextView) findViewById(R.id.tvName);
        mTvEmail = (TextView) findViewById(R.id.tvEmail);
        mTvPhone = (TextView) findViewById(R.id.tvPhone);
        mTvPassword = (TextView) findViewById(R.id.tvPassword);
        mNameTv = (TextView) findViewById(R.id.nameTextView);

        reloadButton = (FloatingActionButton) findViewById(R.id.profileReloadButton);

    }

    public void pickImage() {
        Intent chooserIntent = ImageUtils.getChooserIntent();
        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mFile = ImageUtils.getImageFile(this,data.getData(),mProfileImage);
            WebOperations.updateUserPhoto(UserProfileActivity.this,"PROFILE PICTURE",mFile);
        }
    }



}

