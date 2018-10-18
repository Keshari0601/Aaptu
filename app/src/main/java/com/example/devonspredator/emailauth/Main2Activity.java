package com.example.devonspredator.emailauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends AppCompatActivity {
    TextView txt_email,txt_status,txt_uid;
    Button btn_send,btn_refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified())           //firebase authentication when done , this page is redirected
        // to next activity i.e "profile activity"
        {
            startActivity(new Intent(Main2Activity.this,Profile.class));
            finish();
        }


        txt_email=findViewById(R.id.txt_email);                                    //setting ids to the three textViews and two buttons.
        txt_status=findViewById(R.id.txt_status);
        txt_uid=findViewById(R.id.txt_uid);
        btn_refresh=findViewById(R.id.btn_refresh);
        btn_send=findViewById(R.id.btn_send);
        setInfo();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                       //when clicked on send aka verify button , an authentication email wiill be sed to
                btn_send.setEnabled(false);                                        //current user emailId.
                FirebaseAuth.getInstance().getCurrentUser()                         //toast will appear according to status of verification
                        .sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                btn_send.setEnabled(true);

                                if (task.isSuccessful())
                                    Toast.makeText(Main2Activity.this, "VERIFICATION EMAIL SEND TO :"+FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(Main2Activity.this, "failed to send verification email", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().getCurrentUser()                         //when clicked on refresh button ,it will display the most recent status of verification
                        .reload()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                setInfo();
                            }
                        });
                if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified())
                {
                    startActivity(new Intent(Main2Activity.this,Profile.class));
                    finish();
                }
            }
        });
    }

    private void setInfo() {                                                           //method the set the text to be displayed in test boxes having info
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();                //as emailId ,status of verification and UserId.
        txt_email.setText(new StringBuilder("EMAIL : ").append(user.getEmail()));
        txt_uid.setText(new StringBuilder("UID : ").append(user.getUid()));
        txt_status.setText(new StringBuilder("STATUS : ").append(String.valueOf(user.isEmailVerified())));

    }
}
