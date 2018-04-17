package com.babiescry.babycry;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Thomas on 14-04-2018.
 */

public class Splash extends Activity {

    TextView tv;
    Animation anim;
    ProgressBar pb;
    Button btn;
    String fontPath1 = "fonts/induction.ttf";
    Typeface tf1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash);

        tf1 = Typeface.createFromAsset(this.getAssets(), fontPath1);

        tv = (TextView) findViewById(R.id.heading);
        pb = (ProgressBar) findViewById(R.id.pb) ;
        btn = (Button) findViewById(R.id.btn);

        tv.setTypeface(tf1);

        anim = AnimationUtils.loadAnimation(this, R.anim.scale);
        tv.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                pb.setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        });

    }
}
