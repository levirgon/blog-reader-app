package com.example.noushad.blogbee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noushad.blogbee.Interface.ApiInterface;
import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.Retrofit.ServiceGenerator;
import com.example.noushad.blogbee.model.registerResponseModel.RegSuccessResponse;

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

    private static final String TAG = "RegisterActivity";
    private ApiInterface mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mService = ServiceGenerator.createService(ApiInterface.class);
        mNameEditText = (EditText) findViewById(R.id.account_name);
        mEmailEditText = (EditText) findViewById(R.id.account_email);
        mPhoneEditText = (EditText) findViewById(R.id.account_phone);
        mPasswordEditText = (EditText) findViewById(R.id.account_password);
        mConfirmPasswordEditText = (EditText) findViewById(R.id.account_confirm_password);
        mRegisterButton = (Button) findViewById(R.id.register_button);


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


            return addToDatabase(name, email, password, phone, confirmPassword);

        }

        return false;
    }

    private boolean addToDatabase(String name, String email, String password, String phone, String confirmPassword) {

        //
        Call<RegSuccessResponse> registerResponseCall = mService.createUser(name, email, password, phone, confirmPassword);

        registerResponseCall.enqueue(new Callback<RegSuccessResponse>() {
            @Override
            public void onResponse(Call<RegSuccessResponse> call, Response<RegSuccessResponse> response) {

                if (response.isSuccessful()) {
                    RegSuccessResponse registerResponse = response.body();
                    String email = (registerResponse.getData()).getEmail();
                    Toast.makeText(RegisterActivity.this, email + " has been registered, \n please check email to verify account ", Toast.LENGTH_LONG).show();

                }else{
                    //RegErrorResponse regErrorResponse = response.body();

                }


            }

            @Override
            public void onFailure(Call<RegSuccessResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "something went wrong", Toast.LENGTH_LONG).show();
            }
        });


        return false;
    }


}
