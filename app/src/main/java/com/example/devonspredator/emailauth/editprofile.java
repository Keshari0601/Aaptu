package com.example.devonspredator.emailauth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.flags.impl.DataUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class editprofile extends AppCompatActivity {

    EditText name,age,college;
    Button update,cancel;
    CircleImageView photo;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filepath;
    private static final int GALLERY= 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        photo=(CircleImageView)findViewById(R.id.photo);
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        cancel=(Button)findViewById(R.id.cancel);
        update=(Button)findViewById(R.id.update);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(editprofile.this,Profile.class));
                finish();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("")||age.getText().toString().equals("")||college.getText().toString().equals(""))
                {
                    Toast.makeText(editprofile.this,"Please fill up",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference profile = database.getReference("Profile");
                    profile.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Name").setValue(name.getText().toString());            //uploading the name,category and description of test to firebase.
                    profile.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Age").setValue(age.getText().toString());            //uploading the name,category and description of test to firebase.
                    profile.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("College").setValue(college.getText().toString());            //uploading the name,category and description of test to firebase.
                    Toast.makeText(editprofile.this,"Profile Updated",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(editprofile.this,Profile.class));

                    finish();
                }
            }
        });

        name=(EditText)findViewById(R.id.editText10);
        age=(EditText)findViewById(R.id.editText12);
        college=(EditText)findViewById(R.id.editText13);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference profile = database.getReference("Profile");
        profile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Name").getValue().toString());
                age.setText(dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Age").getValue().toString());
                college.setText(dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("College").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });







    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(editprofile.this,Profile.class));

        finish();


    }

    public void photoclicked(View view) {
            Intent intent =new Intent();
            intent.setType("images/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"SELECT IMAGE"),GALLERY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY && resultCode==RESULT_OK&& data!=null && data.getData()!=null)
        {
            filepath=data.getData();
            try{
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                photo.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            uploadimage();
        }
    }

    private void uploadimage() {
        if(filepath!=null)
        {
            StorageReference ref=storageReference.child("images"+ UUID.randomUUID().toString());
            ref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(editprofile.this,"image updated",Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
