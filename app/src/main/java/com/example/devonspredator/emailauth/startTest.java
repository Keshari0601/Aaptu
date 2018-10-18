package com.example.devonspredator.emailauth;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.LeadingMarginSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class startTest extends AppCompatActivity {
    TextView testname,ques,testtime;
    CheckBox r1,r2,r3,r4;
    int noqs=0,cq=1;
    Button submit,previous,next,done;
    String time,detailssss;
    int j=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference cotegory = database.getReference("Quiz");
        r1=(CheckBox)findViewById(R.id.option1);
        r2=(CheckBox)findViewById(R.id.option2);
        r3=(CheckBox)findViewById(R.id.option3);
        r4=(CheckBox)findViewById(R.id.option4);
        testtime=(TextView)findViewById(R.id.textViewtimer);


        testname=(TextView)findViewById(R.id.textViewnametest);
        previous=(Button)findViewById(R.id.button5);
        submit=(Button)findViewById(R.id.button2);
        next=(Button)findViewById(R.id.button3);

        ques=(TextView)findViewById(R.id.textViewQues);
        cotegory.child(getIntent().getAction()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                testname.setText(dataSnapshot.child("Name").getValue().toString());
                time=dataSnapshot.child("Time").getValue().toString();

                CountDownTimer ct=new CountDownTimer(Integer.parseInt(time)*1000,1000 ) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        testtime.setText(Integer.toString((int)(millisUntilFinished/60000))+":"+Integer.toString((int)((millisUntilFinished/1000)%60)));
                    }

                    @Override
                    public void onFinish() {


                    }
                }.start();

                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(postSnapshot.getKey().indexOf("keyforq")>0){
                        noqs++;
                    }
                }
                final String [] ans=new String[noqs+1];
                final TextView tq[] = new TextView[noqs];
                LinearLayout l1=(LinearLayout)findViewById(R.id.horizontal);
                for (j=0;j<noqs;j++) {
                    tq[j]=new TextView(startTest.this);
                    tq[j].setText(" " +Integer.toString(j+1)+" ");
                    tq[j].setTextSize(30);
                    l1.addView(tq[j]);
                    tq[j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            for(int p=0;p<noqs;p++)
                            {
                                if(!ans[p+1].equals("0")) {
                                    Toast.makeText(startTest.this, "p", Toast.LENGTH_SHORT).show();
                                }
                            }
                            String buttonText = ((TextView)v).getText().toString();
                            cq=Integer.parseInt(buttonText.substring(1,buttonText.length()-1));
                            ques.setText("Q"+Integer.toString(cq) + ": " + dataSnapshot.child("Q"+Integer.toString(cq)+"keyforq").getValue());
                            r1.setText(dataSnapshot.child("Q"+Integer.toString(cq)+"O1").getValue().toString());
                            r2.setText(dataSnapshot.child("Q"+Integer.toString(cq)+"O2").getValue().toString());
                            r3.setText(dataSnapshot.child("Q"+Integer.toString(cq)+"O3").getValue().toString());
                            r4.setText(dataSnapshot.child("Q"+Integer.toString(cq)+"O4").getValue().toString());

                            if(ans[cq].equals("1")){
                                r1.setChecked(true);
                                r2.setChecked(false);
                                r3.setChecked(false);
                                r4.setChecked(false);

                            }
                            else if(ans[cq].equals("2"))
                            {
                                r1.setChecked(false);
                                r2.setChecked(true);
                                r3.setChecked(false);
                                r4.setChecked(false);

                            }
                            else if(ans[cq].equals("3"))
                            {
                                r1.setChecked(false);
                                r2.setChecked(false);
                                r3.setChecked(true);
                                r4.setChecked(false);

                            }
                            else if(ans[cq].equals("4"))
                            {
                                r1.setChecked(false);
                                r2.setChecked(false);
                                r3.setChecked(false);
                                r4.setChecked(true);

                            }
                            else
                            {
                                r1.setChecked(false);
                                r2.setChecked(false);
                                r3.setChecked(false);
                                r4.setChecked(false);

                            }

                        }


                    });
                }

                int temp=0;
                final String [] exactans=new String[noqs+1];
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(postSnapshot.getKey().indexOf("ANS")>0){
                        exactans[++temp]=postSnapshot.getValue().toString();
                        ans[temp]="0";
                    }
                }
                if(noqs==0)
                {
                    ques.setText("this test contain no question");
                    r1.setVisibility(View.INVISIBLE);
                    r2.setVisibility(View.INVISIBLE);
                    r3.setVisibility(View.INVISIBLE);
                    r4.setVisibility(View.INVISIBLE);
                    previous.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.INVISIBLE);
                    submit.setVisibility(View.INVISIBLE);
                }
                else
                {
                    ques.setText("Q"+Integer.toString(cq) + ": " + dataSnapshot.child("Q"+Integer.toString(cq)+"keyforq").getValue());
                    r1.setText(dataSnapshot.child("Q"+Integer.toString(cq)+"O1").getValue().toString());
                    r2.setText(dataSnapshot.child("Q"+Integer.toString(cq)+"O2").getValue().toString());
                    r3.setText(dataSnapshot.child("Q"+Integer.toString(cq)+"O3").getValue().toString());
                    r4.setText(dataSnapshot.child("Q"+Integer.toString(cq)+"O4").getValue().toString());
                    previous.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(cq!=1)
                            {
                                cq--;
                                ques.setText("Q"+Integer.toString(cq) + ": " + dataSnapshot.child("Q"+Integer.toString(cq)+"keyforq").getValue());
                                r1.setText(dataSnapshot.child("Q"+Integer.toString(cq)+"O1").getValue().toString());
                                r2.setText(dataSnapshot.child("Q"+Integer.toString(cq)+"O2").getValue().toString());
                                r3.setText(dataSnapshot.child("Q"+Integer.toString(cq)+"O3").getValue().toString());
                                r4.setText(dataSnapshot.child("Q"+Integer.toString(cq)+"O4").getValue().toString());



                                if(ans[cq].equals("1")){
                                    r1.setChecked(true);
                                    r2.setChecked(false);
                                    r3.setChecked(false);
                                    r4.setChecked(false);

                                }
                                else if(ans[cq].equals("2"))
                                {
                                    r1.setChecked(false);
                                    r2.setChecked(true);
                                    r3.setChecked(false);
                                    r4.setChecked(false);

                                }
                                else if(ans[cq].equals("3"))
                                {
                                    r1.setChecked(false);
                                    r2.setChecked(false);
                                    r3.setChecked(true);
                                    r4.setChecked(false);

                                }
                                else if(ans[cq].equals("4"))
                                {
                                    r1.setChecked(false);
                                    r2.setChecked(false);
                                    r3.setChecked(false);
                                    r4.setChecked(true);

                                }
                                else
                                {
                                    r1.setChecked(false);
                                    r2.setChecked(false);
                                    r3.setChecked(false);
                                    r4.setChecked(false);

                                }

                            }



                        }
                    });
                    r1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(r1.isChecked())
                            {
                                r2.setChecked(false);
                                r3.setChecked(false);
                                r4.setChecked(false);
                                ans[cq]="1";


                            }
                            else
                            {
                                ans[cq]="0";
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
                                ans[cq]="2";


                            }
                            else
                            {
                                ans[cq]="0";

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
                                ans[cq]="3";


                            }
                            else
                            {
                                ans[cq]="0";

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
                                ans[cq]="4";


                            }
                            else
                            {
                                ans[cq]="0";


                            }
                        }
                    });
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(cq!=noqs)
                            {
                                cq++;
                                ques.setText("Q"+Integer.toString(cq) + ": " + dataSnapshot.child("Q"+Integer.toString(cq)+"keyforq").getValue());
                                r1.setText(dataSnapshot.child("Q"+Integer.toString(cq)+"O1").getValue().toString());
                                r2.setText(dataSnapshot.child("Q"+Integer.toString(cq)+"O2").getValue().toString());
                                r3.setText(dataSnapshot.child("Q"+Integer.toString(cq)+"O3").getValue().toString());
                                r4.setText(dataSnapshot.child("Q"+Integer.toString(cq)+"O4").getValue().toString());

                                if(ans[cq].equals("1")){
                                    r1.setChecked(true);
                                    r2.setChecked(false);
                                    r3.setChecked(false);
                                    r4.setChecked(false);

                                }
                                else if(ans[cq].equals("2"))
                                {
                                    r1.setChecked(false);
                                    r2.setChecked(true);
                                    r3.setChecked(false);
                                    r4.setChecked(false);

                                }
                                else if(ans[cq].equals("3"))
                                {
                                    r1.setChecked(false);
                                    r2.setChecked(false);
                                    r3.setChecked(true);
                                    r4.setChecked(false);

                                }
                                else if(ans[cq].equals("4"))
                                {
                                    r1.setChecked(false);
                                    r2.setChecked(false);
                                    r3.setChecked(false);
                                    r4.setChecked(true);

                                }
                                else
                                {
                                    r1.setChecked(false);
                                    r2.setChecked(false);
                                    r3.setChecked(false);
                                    r4.setChecked(false);

                                }

                            }
                        }
                    });

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setContentView(R.layout.result);
                            int score=0,unattempted=0,wrong=0;
                            detailssss=null;
                            for(cq=0;cq<noqs;cq++)
                            {
                                detailssss=detailssss+Integer.toString(cq+1)+": "+"Attempted:"+ans[cq+1]+", "+"Correct:"+exactans[cq+1]+"\n";
                                if(ans[cq+1].equals(exactans[cq+1]))
                                {

                                    score++;
                                }
                                else if(ans[cq+1].equals("0"))
                                {
                                    unattempted++;
                                }
                                else
                                {
                                    wrong++;

                                }
                            }
                            TextView scoret=new TextView(startTest.this);
                            scoret.setText("Correct: "+Integer.toString(score)+", "+"Wrong: "+Integer.toString(wrong)+", "+"Unattempted: "+Integer.toString(unattempted));
                            scoret.setTextSize(30);
                            LinearLayout l=(LinearLayout)findViewById(R.id.linear);
                            l.addView(scoret);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference profile = database.getReference("Profile");
                            profile.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Stats").child(getIntent().getAction()).child("Score").setValue(Integer.toString(score));
                            profile.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Stats").child(getIntent().getAction()).child("Wrong").setValue(Integer.toString(wrong));
                            profile.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Stats").child(getIntent().getAction()).child("Unattempted").setValue(Integer.toString(unattempted));
                            TextView details=new TextView(startTest.this);
                            details.setTextSize(30);
                            done=(Button)findViewById(R.id.done);
                            done.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });




                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
