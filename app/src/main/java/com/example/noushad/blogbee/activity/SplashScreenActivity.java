package com.example.noushad.blogbee.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.utils.SharedPrefManager;

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            startActivity(new Intent(this,FragmentContainerActivity.class));
            finish();
        }

        else {
            Animation tv_anim = AnimationUtils.loadAnimation(this, R.anim.tv_anim);
            Animation icon_anim = AnimationUtils.loadAnimation(this, R.anim.iv_anim);

            TextView tv_title = (TextView) findViewById(R.id.titleTextView);
            tv_title.setAnimation(tv_anim);
            ImageView iv_icon = (ImageView) findViewById(R.id.iconImageView);
            iv_icon.setAnimation(icon_anim);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();
                }
            }, 4000);

        }

    }
}
