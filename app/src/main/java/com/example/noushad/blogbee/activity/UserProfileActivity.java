package com.example.noushad.blogbee.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
    private CardView nameCard;
    private CardView emailCard;
    private CardView phoneCard;
    private CardView passwordCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        this.setTitle("Profile");
        initializeViews();

        displayData();
        setUpCardClickListeners();
    }

    private void setUpCardClickListeners() {
        nameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeDialog("name");
            }
        });
        emailCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeDialog("email");
            }
        });
        phoneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeDialog("phone_no");
            }
        });
        passwordCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeDialog("password");
            }
        });
    }

    private void showChangeDialog(final String key) {

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
                WebOperations.updateUserInformation(getApplicationContext(), key, value);

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
        emailCard = (CardView) findViewById(R.id.emailEditCard);
        phoneCard = (CardView) findViewById(R.id.phoneEditCard);
        nameCard = (CardView) findViewById(R.id.nameEditCard);
        passwordCard = (CardView) findViewById(R.id.passwordEditCard);

    }


}

