package com.example.devonspredator.emailauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Statistics extends AppCompatActivity {
    Long num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference stats = database.getReference("Profile");
        stats.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Stats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                num = dataSnapshot.getChildrenCount();
                int z = create(num,false);


                TextView tv[] = new TextView[z];
                LinearLayout l=(LinearLayout)findViewById(R.id.linear);
                int j=0;
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    tv[j]=new TextView(Statistics.this);
                    tv[j].setText(postSnapshot.getValue().toString());
                    tv[j].setTextSize(30);
                 /*   tv[j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Statistics.this,History.class).setAction(postSnapshot.getKey()));

                        }
                    });*/
                    l.addView(tv[j]);
                    j++;

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public int create(long uka,boolean b) {

        int i = (int) uka;
        return i;
    }



}
