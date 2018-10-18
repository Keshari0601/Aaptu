package com.example.devonspredator.emailauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addquestions extends AppCompatActivity {

    Button submit,addq;
    TextView qn;
    CheckBox r1,r2,r3,r4;
    EditText question,o1,o2,o3,o4;
    int ans=0;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference cotegory = database.getReference("Quiz");

    int i=1;
    //this class is generated to let the user add multiple questions in the test created by the user in previous activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquestions);     //assigning respective ids to the variables
        submit=(Button)findViewById(R.id.submit);
        question=(EditText)findViewById(R.id.editText4);
        o1=(EditText)findViewById(R.id.editText5);
        o2=(EditText)findViewById(R.id.editText3);
        o3=(EditText)findViewById(R.id.editText6);
        o4=(EditText)findViewById(R.id.editText7);
        r1=(CheckBox)findViewById(R.id.cb1);
        r2=(CheckBox)findViewById(R.id.cb2);
        r3=(CheckBox)findViewById(R.id.cb3);
        r4=(CheckBox)findViewById(R.id.cb4);

        addq=(Button)findViewById(R.id.addmoreq);
        qn=(TextView)findViewById(R.id.textView2);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(r1.isChecked())
                {
                    r2.setChecked(false);
                    r3.setChecked(false);
                    r4.setChecked(false);
                    ans=1;


                }
                else
                {
                    ans=0;
                }
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(r2.isChecked())
                {
                    r1.setChecked(false);
                    r3.setChecked(false);
                    r4.setChecked(false);
                    ans=2;


                }
                else
                {
                    ans=0;

                }
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(r3.isChecked())
                {
                    r2.setChecked(false);
                    r1.setChecked(false);
                    r4.setChecked(false);
                    ans=3;


                }
                else
                {
                    ans=0;

                }
            }
        });
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(r4.isChecked())
                {
                    r2.setChecked(false);
                    r3.setChecked(false);
                    r1.setChecked(false);
                    ans=4;


                }
                else
                {
                    ans=0;


                }
            }
        });

        qn.setText("Q"+ Integer.toString(i));
        addq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                    //on clicking add button a new formatted activity starts to enter the question.
                if(ans==0||question.getText().toString().equals("")||o1.getText().toString().equals("")||o2.getText().toString().equals("")||o3.getText().toString().equals("")||o4.getText().toString().equals(""))
                {
                    Toast.makeText(addquestions.this,"PLease enter each data",Toast.LENGTH_SHORT).show();

                }
                else {
                    upload();                               //before starting the new formatted page the previous ques is uploaded to the firebase
                    qn.setText("Q" + Integer.toString(i));
                    question.setText("");
                    o1.setText("");
                    o2.setText("");
                    o3.setText("");
                    o4.setText("");
                    ans=0;
                    r1.setChecked(false);
                    r2.setChecked(false);
                    r3.setChecked(false);
                    r4.setChecked(false);




                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {               //when clicked o submit button the test is finally constructed completely and uploaded to firebase
                if(i==1)
                {
                    Toast.makeText(addquestions.this,"Test cannot be submitted with a single or no question..",Toast.LENGTH_SHORT).show();

                }
                else if(ans==0||question.getText().toString().equals("")||o1.getText().toString().equals("")||o2.getText().toString().equals("")||o3.getText().toString().equals("")||o4.getText().toString().equals(""))
                {   //if user has not entered the question and clicked on submit then the test uploads without last question
                    Toast.makeText(addquestions.this,"paper submitted with neglecting last question",Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(addquestions.this,Profile.class));
                    finish();
                }

                else
                {
                    Toast.makeText(addquestions.this,"paper submitted with last question",Toast.LENGTH_SHORT).show();
                    upload();
                startActivity(new Intent(addquestions.this,Profile.class));
                finish();
            }}
        });
    }
    public void upload()
    {           //this method is called for uploading the question to firebase
        cotegory.child("QUIZ"+Host.numu).child(qn.getText().toString()+"keyforq").setValue(question.getText().toString());
        cotegory.child("QUIZ"+Host.numu).child(qn.getText().toString()+"O1").setValue(o1.getText().toString());
        cotegory.child("QUIZ"+Host.numu).child(qn.getText().toString()+"O2").setValue(o2.getText().toString());
        cotegory.child("QUIZ"+Host.numu).child(qn.getText().toString()+"O3").setValue(o3.getText().toString());
        cotegory.child("QUIZ"+Host.numu).child(qn.getText().toString()+"O4").setValue(o4.getText().toString());
        cotegory.child("QUIZ"+Host.numu).child(qn.getText().toString()+"ANS").setValue(Integer.toString(ans));



        i++;
    }
}
