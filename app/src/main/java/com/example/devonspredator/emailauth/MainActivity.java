package com.example.devonspredator.emailauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final int PER_LOGIN =1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)// if user is already signed in , he will be redirected for email varificatiion
        {
            startActivity(new Intent(MainActivity.this,Main2Activity.class));
            finish();
        }
        else {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()             //firebase signin UI is used here to create a signin intent
                    .setAllowNewEmailAccounts(true).build(), PER_LOGIN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==PER_LOGIN)
        {
            handleSignInResponse(resultCode,data);
            return;
        }
    }

    private void handleSignInResponse(int resultCode, Intent data) {
        if (resultCode==RESULT_OK)                                                          //on entering emailid second activity will start.
        {
            Intent newActivity=new Intent(MainActivity.this,Main2Activity.class);
            startActivity(newActivity);
            finish();
            return;
        }
        else
            Toast.makeText(MainActivity.this,"LOGIN FAILED!!",Toast.LENGTH_SHORT).show();       //if result is not OK or according to our wish then a toast will appear
    }
}
