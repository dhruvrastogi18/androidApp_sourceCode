package com.example.younityin;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class anim extends AppCompatActivity {


    private static final int SPLASH_SCREEN =4000;
    //VARIABLE
    Animation topAnim, bottomAnim;
    TextView slogan;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_animation);

        //ANIMATIONS
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //HOOKS
        image = findViewById(R.id.Logo);
        slogan = findViewById(R.id.Slogan);

        slogan.setAnimation(bottomAnim);
        image.setAnimation(topAnim);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                //Call next screen

                Intent intent=new Intent(anim.this,Main2Activity.class);
                // Attach all the elements those you want to animate in design
                Pair[]pairs=new Pair[2];

                pairs[0]=new Pair<View, String>(image,"logo_image");

                pairs[1]=new Pair<View, String>(slogan,"logo_text");
                //wrap the call in API level 21 or higher
                if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.LOLLIPOP)
                {
                    ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(anim.this,pairs);
                    startActivity(intent,options.toBundle());
                }
            }
        }, SPLASH_SCREEN);


    }
}
