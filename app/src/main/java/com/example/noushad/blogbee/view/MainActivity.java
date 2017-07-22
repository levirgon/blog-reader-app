package com.example.noushad.blogbee.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noushad.blogbee.R;

public class MainActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private TextView mCreateAccountTextView;
    private String mEmail;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    Toast.makeText(MainActivity.this, "Please Enter a valid Email and Password", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(MainActivity.this, BlogsFeedActivity.class);
                    startActivity(intent);

                }


            }
        });

        mCreateAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

}
