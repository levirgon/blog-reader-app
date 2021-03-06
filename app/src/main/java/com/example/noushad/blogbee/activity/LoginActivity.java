package com.example.noushad.blogbee.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noushad.blogbee.Interface.ApiInterface;
import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.Retrofit.ServiceGenerator;
import com.example.noushad.blogbee.model.SimpleError;
import com.example.noushad.blogbee.model.ValidationError.ValidationError;
import com.example.noushad.blogbee.model.ViewModel.UserDetails;
import com.example.noushad.blogbee.model.loginResponseModel.LogInSuccessResponse;
import com.example.noushad.blogbee.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.vstechlab.easyfonts.EasyFonts;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private TextView mCreateAccountTextView;
    private TextView mForgetPasswordTextView;
    private String mEmail;
    private String mPassword;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;
    private ApiInterface mService;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        initializeViews();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEmail = mEmailEditText.getText().toString();
                mPassword = mPasswordEditText.getText().toString();

                //check for valid email and password
                if (mEmail.isEmpty() || mPassword.isEmpty()) {
                    if (mEmail.isEmpty()) {
                        emailInputLayout.setErrorEnabled(true);
                        emailInputLayout.setError("Email cannot be empty");
                    } else {
                        emailInputLayout.setErrorEnabled(false);
                    }
                    if (mPassword.isEmpty()) {
                        passwordInputLayout.setErrorEnabled(true);
                        passwordInputLayout.setError("Password cannot be empty");
                    } else {
                        passwordInputLayout.setErrorEnabled(false);
                    }
                } else {
                    passwordInputLayout.setErrorEnabled(false);
                    emailInputLayout.setErrorEnabled(false);
                    userLogin(mEmail, mPassword);
                }
            }
        });
        mPasswordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    mEmail = mEmailEditText.getText().toString();
                    mPassword = mPasswordEditText.getText().toString();

                    //check for valid email and password
                    if (mEmail.isEmpty() || mPassword.isEmpty()) {
                        if (mEmail.isEmpty()) {
                            emailInputLayout.setErrorEnabled(true);
                            emailInputLayout.setError("Email cannot be empty");
                        } else {
                            emailInputLayout.setErrorEnabled(false);
                        }
                        if (mPassword.isEmpty()) {
                            passwordInputLayout.setErrorEnabled(true);
                            passwordInputLayout.setError("Password cannot be empty");
                        } else {
                            passwordInputLayout.setErrorEnabled(false);
                        }
                    } else {
                        passwordInputLayout.setErrorEnabled(false);
                        emailInputLayout.setErrorEnabled(false);
                        userLogin(mEmail, mPassword);
                    }
                    return true;
                }
                return false;
            }
        });

        mCreateAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        mForgetPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initializeViews() {
        progressDialog = new ProgressDialog(this);
        mService = ServiceGenerator.createService(ApiInterface.class);

        emailInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout);
        passwordInputLayout = (TextInputLayout) findViewById(R.id.passwordTextInputLayout);
        mEmailEditText = (EditText) findViewById(R.id.email_input);

        mPasswordEditText = (EditText) findViewById(R.id.password_input);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setTypeface(EasyFonts.caviarDreamsBold(this));
        mCreateAccountTextView = (TextView) findViewById(R.id.create_account);
        mCreateAccountTextView.setTypeface(EasyFonts.caviarDreamsBold(this));
        mForgetPasswordTextView = (TextView) findViewById(R.id.tv_forgetPass);
        mForgetPasswordTextView.setTypeface(EasyFonts.caviarDreamsBold(this));

        TextView textView = (TextView) findViewById(R.id.tv_signIn);
        textView.setTypeface(EasyFonts.caviarDreamsBold(this));
    }

    private void userLogin(String email, String password) {

        progressDialog.setMessage("Logging In...");
        progressDialog.show();
        String token = SharedPrefManager.getInstance(this).getDeviceToken();
        Call<LogInSuccessResponse> responseCall = mService.userLogin(email, password,token);

        responseCall.enqueue(new Callback<LogInSuccessResponse>() {
            @Override
            public void onResponse(Call<LogInSuccessResponse> call, Response<LogInSuccessResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    LogInSuccessResponse aInformation = response.body();
                    SharedPrefManager.getInstance(getApplicationContext()).userLoginDataUpdate(aInformation);
                    setLoggedInUserInformation();
                } else if (response.code() == 422) {
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        ValidationError errorResponse = gson.fromJson(mJson, ValidationError.class);
                        Toast.makeText(getApplicationContext(), errorResponse.toString(), Toast.LENGTH_SHORT).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        SimpleError simpleErrorRes = gson.fromJson(mJson, SimpleError.class);
                        Toast.makeText(getApplicationContext(), simpleErrorRes.getError().toString(), Toast.LENGTH_SHORT).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<LogInSuccessResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private static final String TAG = "LoginActivity";

    private void setLoggedInUserInformation() {

        progressDialog.setMessage("Getting User Information...");
        progressDialog.show();

        Call<UserDetails> responseCall = mService.GetLoggedInUserData(SharedPrefManager.getInstance(this).getAuthToken());

        responseCall.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    UserDetails user = response.body();

                    SharedPrefManager.getInstance(getApplicationContext()).userOwnDataUpdate(user);
                    finish();
                    startActivity(new Intent(getApplicationContext(), FragmentContainerActivity.class));
                } else {
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        SimpleError simpleerrorRes = gson.fromJson(mJson, SimpleError.class);
                        Toast.makeText(getApplicationContext(), simpleerrorRes.getError().toString(), Toast.LENGTH_SHORT).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }


}
