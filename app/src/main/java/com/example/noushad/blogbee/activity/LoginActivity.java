package com.example.noushad.blogbee.activity;

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
import com.example.noushad.blogbee.model.loginResponseModel.LogInSuccessResponse;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                    Toast.makeText(LoginActivity.this, "Please Enter a valid Email and Password", Toast.LENGTH_SHORT).show();
                } else {

//                    Intent intent = new Intent(LoginActivity.this, FragmentContainerActivity.class);
//                    startActivity(intent);
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

        Call<LogInSuccessResponse> responseCall = mService.userLogin(email, password);

        responseCall.enqueue(new Callback<LogInSuccessResponse>() {
            @Override
            public void onResponse(Call<LogInSuccessResponse> call, Response<LogInSuccessResponse> response) {
                if (response.isSuccessful()) {
                    LogInSuccessResponse logInSuccessResponse = response.body();
                    Toast.makeText(LoginActivity.this, logInSuccessResponse.getTokenType().toString(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, FragmentContainerActivity.class);
                    startActivity(intent);
                }else{

                }
            }

            @Override
            public void onFailure(Call<LogInSuccessResponse> call, Throwable t) {

            }
        });

    }

}
