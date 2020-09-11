package com.example.younityin;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactUs extends AppCompatActivity {
    private Activity activity;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);




        mToolbar = (Toolbar) findViewById(R.id.contactus_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("CONTACT US");


        drawerLayout = (DrawerLayout) findViewById(R.id.coordinator_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.nlayout);
        View navView = navigationView.inflateHeaderView(R.layout.explore_navigation_header);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });



        final EditText your_name        = (EditText) findViewById(R.id.your_name);
        final EditText your_number     = (EditText) findViewById(R.id.your_number);
        final EditText your_college     = (EditText) findViewById(R.id.your_college);
        final EditText your_email       = (EditText) findViewById(R.id.your_email);
        final EditText your_subject     = (EditText) findViewById(R.id.your_subject);
        final EditText your_message     = (EditText) findViewById(R.id.your_message);



        Button email = (Button) findViewById(R.id.post_message);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name      = your_name.getText().toString();
                String number      = your_number.getText().toString();
                String college      = your_college.getText().toString();
                String email     = your_email.getText().toString();
                String subject   = your_subject.getText().toString();
                String message   = your_message.getText().toString();
                if (TextUtils.isEmpty(name)){
                    your_name.setError("Enter Your Name");
                    your_name.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(number)){
                    your_number.setError("Enter Your Number");
                    your_number.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(name)){
                    your_college.setError("Enter Your College Name");
                    your_college.requestFocus();
                    return;
                }

                Boolean onError = false;
                if (!isValidEmail(email)) {
                    onError = true;
                    your_email.setError("Invalid Email");
                    return;
                }

                if (TextUtils.isEmpty(subject)){
                    your_subject.setError("Enter Your Subject");
                    your_subject.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(message)){
                    your_message.setError("Enter Your Message");
                    your_message.requestFocus();
                    return;
                }

                Intent sendEmail = new Intent(android.content.Intent.ACTION_SEND);

                /* Fill it with Data */
                sendEmail.setType("plain/text");
                sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"hello@younity.in"});
                sendEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                sendEmail.putExtra(android.content.Intent.EXTRA_TEXT,
                        "Name:"+name+'\n'+"Number:"+number+'\n'+"College:"+college+'\n'+"Email ID:"+email+'\n'+"Message:"+'\n'+message);

                /* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(sendEmail, "Send mail..."));


            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void UserMenuSelector(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.explore_home:
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                openexlpore();
                break;
            case R.id.explore_mentorship:
                Toast.makeText(this, "MENTORSHIP", Toast.LENGTH_SHORT).show();
                openmentorship();
                break;
            case R.id.explore_Entrepreneuship:
                Toast.makeText(this, "ENTREPRENEUSHIP", Toast.LENGTH_SHORT).show();
                openentrepre();
                break;
            case R.id.explore_aboutteam:
                Toast.makeText(this, "ABOUT TEAM", Toast.LENGTH_SHORT).show();
                openaboutteam();
                break;

            case R.id.explore_mentors:
                Toast.makeText(this, "MENTORS", Toast.LENGTH_SHORT).show();
                openmentors();
                break;


            case R.id.explore_aboutyounity:
                Toast.makeText(this, "ABOUT YOUNITY", Toast.LENGTH_SHORT).show();
                openaboutyounity();
                break;
            case R.id.explore_conatct:
                Toast.makeText(this, "CONTACT", Toast.LENGTH_SHORT).show();
                opencontactus();
                break;

        }
    }
    public void openBrowser(View view){

        //Get url from tag
        String url = (String)view.getTag();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);

        //pass the url to intent data
        intent.setData(Uri.parse(url));

        startActivity(intent);
    }



    // validating email id

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    private void openentrepre() {
        Intent intent = new Intent(this,entrepreneurship.class);
        startActivity(intent);
    }

    private void openaboutteam() {
        Intent intent = new Intent(this,about_team.class);
        startActivity(intent);
    }

    private void opencontactus() {
        Intent intent = new Intent(this,ContactUs.class);
        startActivity(intent);
    }

    private void openaboutyounity() {
        Intent intent = new Intent(this,About.class);
        startActivity(intent);
    }

    private void openmentors() {
        Intent intent = new Intent(this,mentors.class);
        startActivity(intent);
    }

    private void openmentorship() {
        Intent intent = new Intent(this,mentorship.class);
        startActivity(intent);
    }

    private void openexlpore() {
        Intent intent = new Intent(this,explore.class);
        startActivity(intent);
    }
}
