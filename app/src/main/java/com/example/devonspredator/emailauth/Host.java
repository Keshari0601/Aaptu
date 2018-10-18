


package com.example.devonspredator.emailauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//this class is designed to let the use host a test and input question for construction of test
public class Host extends AppCompatActivity {

    Button add;
    EditText name,description,coteg,time;
    Long num;

    static String n,cot,des,numu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        add=(Button)findViewById(R.id.button4);                                     //assigning respective ids to variables
        name=(EditText)findViewById(R.id.editText);
        coteg=(EditText)findViewById(R.id.editText9);
        time=(EditText)findViewById(R.id.editText11);
        description=(EditText)findViewById(R.id.editText8);
        FirebaseDatabase database = FirebaseDatabase.getInstance();                 //getting reference of the firebase attached to this app.
        final DatabaseReference cotegory = database.getReference("Quiz");
        cotegory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                num = dataSnapshot.getChildrenCount();                                //counting the number of children of  particular root of firebase

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                               //when clicked on add button , user is allowed to add more questions in there test
                if(name.getText().toString().equals("")||time.getText().toString().equals("")||description.getText().toString().equals("")||coteg.getText().toString().equals(""))
                {   //if the user left the name ,category or description of test empty a toast will appear
                    Toast.makeText(Host.this,"PLease enter name, cotegory and description",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    n=name.getText().toString();
                    cot=coteg.getText().toString();                                  //user inputs the name category and description of the test he/she is creating
                    des=description.getText().toString();
                    numu=Long.toString(num);
                    cotegory.child("QUIZ"+Long.toString(num)).child("Name").setValue(n);            //uploading the name,category and description of test to firebase.
                    cotegory.child("QUIZ"+Long.toString(num)).child("Category").setValue(cot);
                    cotegory.child("QUIZ"+Long.toString(num)).child("Description").setValue(des);
                    cotegory.child("QUIZ"+Long.toString(num)).child("Time").setValue(time.getText().toString());
                    startActivity(new Intent(Host.this,addquestions.class));
                    finish();
                }

            }
        });
    }

}
