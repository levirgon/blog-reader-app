package com.example.noushad.blogbee.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noushad.blogbee.Interface.ApiInterface;
import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.Retrofit.ServiceGenerator;
import com.example.noushad.blogbee.model.registerResponseModel.RegError;
import com.example.noushad.blogbee.model.registerResponseModel.RegResponse;
import com.example.noushad.blogbee.utils.ImageUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vstechlab.easyfonts.EasyFonts;

import org.json.JSONObject;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mPhoneEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private Button mRegisterButton;
    private CircleImageView mProfileImage;
    private File mFile;

    private static final String TAG = "RegisterActivity";
    private ApiInterface mService;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        initializeViews();


        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getInformation()) {

                    CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {

                            Toast.makeText(RegisterActivity.this, " Login using correct details ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }.start();

                }

            }
        });

    }

    private void initializeViews() {
        mService = ServiceGenerator.createService(ApiInterface.class);
        mNameEditText = (EditText) findViewById(R.id.account_name);
        mEmailEditText = (EditText) findViewById(R.id.account_email);
        mPhoneEditText = (EditText) findViewById(R.id.account_phone);
        mPasswordEditText = (EditText) findViewById(R.id.account_password);
        mConfirmPasswordEditText = (EditText) findViewById(R.id.account_confirm_password);
        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setTypeface(EasyFonts.caviarDreamsBold(this));
        mProfileImage = (CircleImageView) findViewById(R.id.register_profile_image);
        mProfileImage.setImageResource(R.drawable.no_profile_image);
        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        mProgressDialog = new ProgressDialog(this);
    }

    private static final int PICK_IMAGE = 1;

    public void pickImage() {
        Intent chooserIntent = ImageUtils.getChooserIntent();
        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mFile = ImageUtils.getImageFile(this, data.getData(), mProfileImage);

        }
    }

    private boolean getInformation() {

        String name = "";
        String email = "";
        String phone = "";
        String password = mPasswordEditText.getText().toString();
        String confirmPassword = mConfirmPasswordEditText.getText().toString();
        name = mNameEditText.getText().toString();
        email = mEmailEditText.getText().toString();
        phone = mPhoneEditText.getText().toString();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {

            Toast.makeText(this, "No Fields can be empty!! ", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword) && (!password.isEmpty() || !confirmPassword.isEmpty())) {

            Toast.makeText(this, "Passwords Do not match", Toast.LENGTH_SHORT).show();

        } else {
            return registerUser(name, email, password, phone, confirmPassword, mFile);
        }
        return false;
    }

    private boolean registerUser(String name, String email, String password, String phone, String confirmPassword, File file) {

        mProgressDialog.setMessage("Signing Up...");
        mProgressDialog.show();

//        MultipartBody.Part body = null;
//
        //apparently the api has problems uploading file here.
//
//        if (file != null) {
//            RequestBody requestFile = RequestBody.create(MediaType.parse("image"), file);
//            body = MultipartBody.Part.createFormData("image_thumb", file.getName(), requestFile);
//
//        }
        Call<RegResponse> registerResponseCall = mService.createUser(name, email, password, phone, confirmPassword);

        registerResponseCall.enqueue(new Callback<RegResponse>() {
            @Override
            public void onResponse(Call<RegResponse> call, Response<RegResponse> response) {
                mProgressDialog.dismiss();
                RegResponse registerResponse = response.body();
                if (response.isSuccessful()) {
                    String email = registerResponse.getEmail();
                    Toast.makeText(RegisterActivity.this, email + " has been registered, \n please check email to verify account ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);

                } else {
                    Gson gson = new GsonBuilder().create();
                    RegError pojo;
                    try {
                        JSONObject errorObj = new JSONObject(response.errorBody().string());
                        pojo = gson.fromJson(errorObj.getJSONObject("error").toString(), RegError.class);
                        Toast.makeText(getApplicationContext(), pojo.toString(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegResponse> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return false;
    }


}
