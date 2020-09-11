package com.example.younityin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindActivity extends AppCompatActivity {
    private Toolbar mToolbar;
private ImageButton Search_Button;
private RecyclerView List_View;
    private DatabaseReference SearchRef;
private EditText Searh_Field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);


        SearchRef = FirebaseDatabase.getInstance().getReference().child("Users");


        mToolbar = (Toolbar) findViewById(R.id.find_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Search");




        List_View=(RecyclerView)findViewById(R.id.search_list);
        List_View.setHasFixedSize(true);
        List_View.setLayoutManager(new LinearLayoutManager(this));


        Search_Button=(ImageButton)findViewById(R.id.button_search);
        Searh_Field=(EditText)findViewById(R.id.search);



        Search_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_search= Searh_Field.getText().toString();
                SearchPeople(input_search);
            }
        });

    }

    private void SearchPeople(String input_search) {

        Toast.makeText(this,"Searching",Toast.LENGTH_SHORT).show();
        final Query searchPeople=SearchRef.orderByChild("fullname").startAt(input_search).endAt(input_search+" \uf8ff");
        FirebaseRecyclerOptions<FindPeople> options =
                new FirebaseRecyclerOptions.Builder<FindPeople>()
                        .setQuery(SearchRef,FindPeople.class)
                        .build();
        FirebaseRecyclerAdapter<FindPeople,PostsViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<FindPeople,PostsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PostsViewHolder holder, int position, @NonNull FindPeople model) {




                    final String PostKey = getRef(position).getKey();
                        holder.username.setText(model.getFullname());

                        Picasso.get().load(model.getProfileimage()).placeholder(R.drawable.profile).into(holder.postdpimage);

                        holder.mView.setOnClickListener(new View.OnClickListener()

                    {
                        @Override
                        public void onClick (View v)
                        {
                            Intent clickPostIntent = new Intent(FindActivity.this, ClickPostActivity.class);
                            clickPostIntent.putExtra("PostKey", PostKey);
                            startActivity(clickPostIntent);

                        }
                    });


                }

                    @NonNull
                    @Override
                    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_users_search, parent, false);
                       PostsViewHolder viewHolder = new PostsViewHolder(view);
                        return viewHolder;
                    }
                };
        firebaseRecyclerAdapter.startListening();

        List_View.setAdapter(firebaseRecyclerAdapter);

    }
    public static class PostsViewHolder extends RecyclerView.ViewHolder
    {
        TextView username, status;

        CircleImageView postdpimage;
        View mView;

        public PostsViewHolder(@NonNull View itemView){
            super(itemView);
            mView=itemView;
            username = itemView.findViewById(R.id.find_name);
            status = itemView.findViewById(R.id.find_status);
            postdpimage = itemView.findViewById(R.id.find_dp);

        }
    }




           }


