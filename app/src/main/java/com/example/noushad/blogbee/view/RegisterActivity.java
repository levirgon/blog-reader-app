package com.example.noushad.blogbee.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.model.Blogger;

public class RegisterActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mPhoneEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private Button mRegisterButton;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

                    Log.d(TAG, "onClick: returned true");

                    CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {

                            Toast.makeText(RegisterActivity.this, " Login using correct details ", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
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


            Blogger blogger = new Blogger(name, email, phone, password);

            return addToDatabase(blogger);


        }

        return false;
    }

    private boolean addToDatabase(Blogger blogger) {

        if (!bloggerAlreadyExists(blogger)) {
            //add information to database


            Toast.makeText(this, "User Added To Database", Toast.LENGTH_SHORT).show();

            return true;

        }

        return false;
    }

    private boolean bloggerAlreadyExists(Blogger blogger) {

        //check database for duplicate data.


        return false;
    }
}
