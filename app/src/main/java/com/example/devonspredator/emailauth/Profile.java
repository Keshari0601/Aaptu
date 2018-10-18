package com.example.devonspredator.emailauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*this page is designed to let user choose whether he/she wants to hot a test or want to give a test*/
public class Profile extends AppCompatActivity {
    Button host,givetest,logout,editprofile,stats;
    Boolean found=false;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference profile = database.getReference("Profile");
        name=(TextView)findViewById(R.id.textView);
        stats=(Button)findViewById(R.id.stats);
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,Statistics.class));

            }
        });

        profile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                   if(postSnapshot.getKey().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                   {
                       name.setText("Welcome "+postSnapshot.child("Name").getValue().toString());
                       found=true;
                       break;
                   }
                }
                if(!found)
                {
                    startActivity(new Intent(Profile.this,editprofile.class));
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        host=(Button)findViewById(R.id.host);                       //assigning respective ids to the three buttons
        logout=(Button)findViewById(R.id.logout);
        givetest=(Button)findViewById(R.id.givetest);
        editprofile=(Button)findViewById(R.id.editprofile);


        host.setOnClickListener(new View.OnClickListener() {
            @Override                                                               //when clicked on host button ,new intent starts which lets the user to input the questions
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,Host.class));
             }
        });
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override                                                               //when clicked on host button ,new intent starts which lets the user to input the questions
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,editprofile.class));
                finish();
            }
        });
        givetest.setOnClickListener(new View.OnClickListener() {                    //when clicked on give test , new intent starts which lets te user to begin his/her test.
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,GiveTest.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {                      //this button lets the user to logout in case he/she don't want to give test or host the test
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this,MainActivity.class));
                finish();
            }
        });

    }
}
