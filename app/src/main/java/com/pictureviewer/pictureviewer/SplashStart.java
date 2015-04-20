package com.pictureviewer.pictureviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.pictureviewer.pictureviewer.instagram.ViewerInstagram;


public class SplashStart extends Activity implements Animation.AnimationListener {

    private static int SPLASH_TIME_OUT = 3000;
    ImageView imgLogo;
    Animation animRotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_start);
        Splash();
        AnimationSplashScreen();
    }

    //pokreće splash screen dok traje učitavanje podataka
    private void Splash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashStart.this, ViewerInstagram.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    //pokreće animaciju
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
