

package com.example.younityin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

public class mentors extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mToolbar;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentors);


        viewPager = (ViewPager) findViewById(R.id.viewwpager);
        adapter_mentors adapter_mentorss = new adapter_mentors(this);
        viewPager.setAdapter(adapter_mentorss);

        mToolbar = (Toolbar) findViewById(R.id.metor_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Mentors");

        drawerLayout = (DrawerLayout) findViewById(R.id.d_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(mentors.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
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











