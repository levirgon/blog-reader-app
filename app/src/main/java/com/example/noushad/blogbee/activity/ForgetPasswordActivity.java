package com.example.noushad.blogbee.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noushad.blogbee.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private TextInputLayout emailInputLayout;
    private Button mSendButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mSendButton = (Button) findViewById(R.id.password_recover_button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"I will work soon",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
