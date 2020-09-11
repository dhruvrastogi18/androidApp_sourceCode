package com.example.younityin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;





import androidx.appcompat.widget.Toolbar;



import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Currency;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView postList;
    private Toolbar mToolbar;
private Boolean likechekker;
    private CircleImageView NavProfileImage;
    private TextView NavProfileUserName;
    private ImageButton AddNewPostButton;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, PostsRef,LikesRef;

    String currentUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        LikesRef = FirebaseDatabase.getInstance().getReference().child("Likes");

        currentUserID = mAuth.getCurrentUser().getUid();


        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Home");



        drawerLayout = (DrawerLayout) findViewById(R.id.dlayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.nlayout);
        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);
        postList = (RecyclerView) findViewById(R.id.all_post_main);
        postList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postList.setLayoutManager(linearLayoutManager);


        NavProfileImage = (CircleImageView) navView.findViewById(R.id.dp);
        NavProfileUserName = (TextView) navView.findViewById(R.id.nav_full_name);

        //AddNewPostButton = (ImageButton) findViewById(R.id._add_new_post);




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });



        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                {

                    if (dataSnapshot.hasChild("fullname"))
                    {
                        if(dataSnapshot.exists()) {
                            String fullname = dataSnapshot.child("fullname").getValue().toString();
                            NavProfileUserName.setText(fullname);
                        }
                        if (dataSnapshot.hasChild("profileimage")) {
                            if(dataSnapshot.exists()){
                                String image = dataSnapshot.child("profileimage").getValue().toString();
                                Picasso.get().load(image).placeholder(R.drawable.profile).into(NavProfileImage);
                            }
                        }}



                    else {
                        Toast.makeText(MainActivity.this, "Profile name do not exists...", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    protected void onStart()
    {
        super.onStart();

        displayalluserpost();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null)
        {
            SendUserToAnimActivity();
        }
        else
        {
            CheckUserExistence();
        }
    }

    private void displayalluserpost()
    {
        FirebaseRecyclerOptions<Posts> options =
                new FirebaseRecyclerOptions.Builder<Posts>()
                        .setQuery(PostsRef,Posts.class)
                        .build();
        FirebaseRecyclerAdapter<Posts, PostsViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Posts, PostsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PostsViewHolder holder, int position, @NonNull Posts model)
                    {
                        final String PostKey = getRef(position).getKey();
                        holder.setLikeStatus(PostKey);
                        holder.username.setText(model.getFullname());
                        holder.usertime.setText(model.getTime());
                        holder.userdate.setText(model.getDate());
                        holder.postdescription.setText(model.getDescription());
                        Picasso.get().load(model.getProfileimage()).placeholder(R.drawable.profile).into(holder.postdpimage);
                        Picasso.get().load(model.getpostimage()).into(holder.postimage);
holder.comment.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        OpenComments();
    }
});
                        holder.like.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                likechekker=true;
                               LikesRef.addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                                 if(likechekker.equals(true))
                                       {
                                           if(snapshot.child(PostKey).hasChild(currentUserID)){
                                               LikesRef.child(PostKey).child(currentUserID).removeValue();
                                               likechekker=false;
                                           }
                                           else {
                                               LikesRef.child(PostKey).child(currentUserID).setValue(true);
                                               likechekker=false;
                                           }
                                       }
                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError error) {

                                   }
                               });
                            }
                        });

                        holder.mView.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent clickPostIntent = new Intent(MainActivity.this, ClickPostActivity.class);
                                clickPostIntent.putExtra("PostKey", PostKey);
                                startActivity(clickPostIntent);

                            }
                        });



                    }

                    @NonNull
                    @Override
                    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_post_layout, parent, false);
                        PostsViewHolder viewHolder = new PostsViewHolder(view);
                        return viewHolder;
                    }
                };
        firebaseRecyclerAdapter.startListening();

        postList.setAdapter(firebaseRecyclerAdapter);

    }

    private void OpenComments() {
        Intent addNewPostIntent = new Intent(MainActivity.this, Comments.class);
        startActivity(addNewPostIntent);

    }

    public static class PostsViewHolder extends RecyclerView.ViewHolder
    {
        TextView username, userdate, usertime, postdescription,likecount;
        ImageView  postimage;
        CircleImageView postdpimage;
        ImageButton like,comment;
        int likescount;
        String currentUserId;
        DatabaseReference LIKESREF;

        View mView;

        public void setLikeStatus(final String PostKey) {
LIKESREF.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.child(PostKey).hasChild(currentUserId)){
            likescount=(int)snapshot.child(PostKey).getChildrenCount();
            like.setImageResource(R.drawable.heart);
            likecount.setText(Integer.toString(likescount));
        }
        else {
            likescount=(int)snapshot.child(PostKey).getChildrenCount();
            like.setImageResource(R.drawable.dislike);
            likecount.setText(Integer.toString(likescount));
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
        }
        public PostsViewHolder(@NonNull View itemView){
            super(itemView);
            mView=itemView;
            username = itemView.findViewById(R.id.post_username);
            userdate = itemView.findViewById(R.id.date);
            usertime = itemView.findViewById(R.id.time);

            like = itemView.findViewById(R.id.like);
            likecount = itemView.findViewById(R.id.likecount);
            LIKESREF = FirebaseDatabase.getInstance().getReference().child("Likes");
            currentUserId= FirebaseAuth.getInstance().getCurrentUser().getUid();

           comment = itemView.findViewById(R.id.comment);
            postdescription = itemView.findViewById(R.id.Description);
            postdpimage = itemView.findViewById(R.id.post_dp);
            postimage = itemView.findViewById(R.id.posst_image);

        }


    }


    private void SendUserToPostActivity() {
        Intent addNewPostIntent = new Intent(MainActivity.this, PostActivity.class);
        startActivity(addNewPostIntent);
    }




    private void SendUserToLoginActivity() {
        Intent setupIntent = new Intent(MainActivity.this, Login.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }

    private void CheckUserExistence() {
        final String current_user_id = mAuth.getCurrentUser().getUid();

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(!dataSnapshot.hasChild(current_user_id))
                {
                    SendUserToSetupActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void SendUserToSetupActivity() {

        Intent setupIntent = new Intent(MainActivity.this, Setup.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }


    private void SendUserToAnimActivity()
    {
        Intent loginIntent = new Intent(MainActivity.this, anim.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_profile:
                Toast.makeText(this,"PROFILE",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_newpost:
                SendUserToPostActivity();
                Toast.makeText(this,"POST",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_home:
                Toast.makeText(this,"HOME",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_friends:
                Toast.makeText(this,"friends",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_ff:
                Toast.makeText(this,"Follow People",Toast.LENGTH_SHORT).show();
                openfind();
                break;


            case R.id.nav_sett:
                Toast.makeText(this,"SETTING",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_logout:
                mAuth.signOut();
                SendUserToAnimActivity();
                break;

        }
    }

    private void openfind() {
        Intent loginIntent = new Intent(MainActivity.this, FindActivity.class);
        startActivity(loginIntent);
    }
}
