package com.example.younityin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Activity extends AppCompatActivity {
    private CircleImageView   button,button1,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button=(CircleImageView) findViewById(R.id.exploreb);
        button1=(CircleImageView) findViewById(R.id.join);
        button2=(CircleImageView) findViewById(R.id.helpbutton);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openexplore();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openjoin();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openhelp();
            }
        });

    }

    private void openhelp() {
        Intent setupIntent = new Intent(this, ContactUs.class);
        startActivity(setupIntent);
    }

    private void openjoin() {

        Intent setupIntent = new Intent(this, Login.class);
        startActivity(setupIntent);
    }

    private void openexplore() {
        Intent setupIntent = new Intent(this, explore.class);
        startActivity(setupIntent);
    }
}
