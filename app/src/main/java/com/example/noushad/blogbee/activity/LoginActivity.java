package com.example.noushad.blogbee.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noushad.blogbee.Interface.ApiInterface;
import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.Retrofit.ServiceGenerator;
import com.example.noushad.blogbee.model.CreatorInfo;
import com.example.noushad.blogbee.model.SimpleError;
import com.example.noushad.blogbee.model.ValidationError.ValidationError;
import com.example.noushad.blogbee.model.loginResponseModel.LogInError;
import com.example.noushad.blogbee.model.loginResponseModel.LogInSuccessResponse;
import com.example.noushad.blogbee.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private TextView mCreateAccountTextView;
    private String mEmail;
    private String mPassword;
    private ApiInterface mService;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, FragmentContainerActivity.class));
        }
        progressDialog = new ProgressDialog(this);
        mService = ServiceGenerator.createService(ApiInterface.class);
        mEmailEditText = (EditText) findViewById(R.id.email_entry);
        mPasswordEditText = (EditText) findViewById(R.id.password_entry);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mCreateAccountTextView = (TextView) findViewById(R.id.create_account);


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmail = mEmailEditText.getText().toString();
                mPassword = mPasswordEditText.getText().toString();

                //check for valid email and password
                if (mEmail.isEmpty() || mPassword.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please Fill up All Required fields", Toast.LENGTH_SHORT).show();
                } else {
                    userLogin(mEmail, mPassword);

                }


            }
        });

        mCreateAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void userLogin(String email, String password) {

        progressDialog.setMessage("Signing Up...");
        progressDialog.show();
        Call<LogInSuccessResponse> responseCall = mService.userLogin(email, password);

        responseCall.enqueue(new Callback<LogInSuccessResponse>() {
            @Override
            public void onResponse(Call<LogInSuccessResponse> call, Response<LogInSuccessResponse> response) {

                progressDialog.dismiss();
                if(response.isSuccessful()){

                    LogInSuccessResponse aInformation = response.body();
                    SharedPrefManager.getInstance(getApplicationContext()).userLoginDataUpdate(aInformation);
                    setLoggedInUserInformation();

                    // startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }else if (response.code()==422){
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        ValidationError errorResponse = gson.fromJson(mJson, ValidationError.class);
                        Toast.makeText(getApplicationContext(),errorResponse.toString(),Toast.LENGTH_SHORT).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }else{
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        SimpleError simpleerrorRes = gson.fromJson(mJson, SimpleError.class);
                        Toast.makeText(getApplicationContext(),simpleerrorRes.getError().toString(),Toast.LENGTH_SHORT).show();
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

    private void setLoggedInUserInformation() {


        progressDialog.setMessage("Get User Information...");
        progressDialog.show();

        //building retrofit object
        Call<CreatorInfo> responseCall = mService.GetLoggedInUserData(SharedPrefManager.getInstance(this).getAuthToken());

        responseCall.enqueue(new Callback<CreatorInfo>() {
            @Override
            public void onResponse(Call<CreatorInfo> call, Response<CreatorInfo> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){

                    CreatorInfo others = response.body();
                    SharedPrefManager.getInstance(getApplicationContext()).userOwnDataUpdate(others);
                    Toast.makeText(getApplicationContext(),"login successfully",Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), FragmentContainerActivity.class));
                }else{
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        SimpleError simpleerrorRes = gson.fromJson(mJson, SimpleError.class);
                        Toast.makeText(getApplicationContext(),simpleerrorRes.getError().toString(),Toast.LENGTH_SHORT).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<CreatorInfo> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
