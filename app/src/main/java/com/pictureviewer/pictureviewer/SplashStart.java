package com.pictureviewer.pictureviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.pictureviewer.pictureviewer.instagram.Http;
import com.pictureviewer.pictureviewer.instagram.Viewer;


/**
 * Created by Mario on 25.4.2015..
 */
public class SplashStart extends Activity implements Animation.AnimationListener {


    private static int SPLASH_TIME_OUT = 5000;
    ImageView imgLogo;
    Animation animRotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_start);
        Finish();
        AnimationSplashScreen();
        Splash();
    }

    //get JSON and set condition for Splash
    private boolean Finish() {
        String data = Http.readInstagram();
        if (data == null) {
            return false;
        } else {
            return true;
        }
    }

    //run Splash until data is collected
    private void Splash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Finish() != true) {
                    return;
                } else {
                    Intent i = new Intent(SplashStart.this, Viewer.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    //run animation
    private void AnimationSplashScreen() {
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        animRotate = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate);
        animRotate.setAnimationListener(this);
        imgLogo.startAnimation(animRotate);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == animRotate) {
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

}
