package com.example.noushad.blogbee.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.model.ViewModel.UserViewModel;
import com.example.noushad.blogbee.utils.SharedPrefManager;
import com.example.noushad.blogbee.utils.WebOperations;
import com.vstechlab.easyfonts.EasyFonts;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    private CircleImageView mProfileImage;
    private TextView mTvName;
    private TextView mTvEmail;
    private TextView mTvPhone;
    private TextView mNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        this.setTitle("Profile");
        initializeViews();
        UserViewModel user = SharedPrefManager.getInstance(this).getUser();

        mTvName.setText(user.getName());
        mTvEmail.setText(user.getEmail());
        mTvPhone.setText(user.getPhoneNo());
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
        mNameTv = (TextView) findViewById(R.id.nameTextView);

    }


}

