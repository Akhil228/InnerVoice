package com.example.innervoice2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class add extends AppCompatActivity {
     Button track,upload;
     EditText name,lyrics;
     TextView trackname;
     FirebaseDatabase database;
     DatabaseReference databaseReference;
     FirebaseStorage storage;
     StorageReference storageReference;
     Uri u;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        track=findViewById(R.id.selecttrack);
        lyrics=findViewById(R.id.lyrics);
        upload=findViewById(R.id.upload);
        name=findViewById(R.id.name);
        trackname=findViewById(R.id.trackname);
        database=FirebaseDatabase.getInstance();
        databaseReference= database.getReference();
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();



        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(add.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectfile();
                }else{
                    ActivityCompat.requestPermissions(add.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadfile(u);
            }
        });

    }

    private void uploadfile(Uri u) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading File");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMax(100);
        pd.setCancelable(false);
        pd.show();
        final String songname= name.getText().toString().trim();
        final String songlyrics= lyrics.getText().toString().trim();
        if(songname.isEmpty() || songlyrics.isEmpty()) {
            Toast.makeText(add.this, "Please fill the Details", Toast.LENGTH_SHORT).show();
        }
        String myfolder="tracklist";
        final StorageReference storageref=storageReference.child(myfolder).child(songname);
        storageref.putFile(u).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                try {
                    storageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            info i= null;
                            try{
                                String trackpath = uri.toString();
                                i=new info(songname,songlyrics,trackpath);
                            }catch(Exception e){
                                e.printStackTrace();
                            };
                            databaseReference.child("details").child(songname).setValue(i).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    try {
                                        Toast.makeText(add.this, "Your Details are Saved Successfully....", Toast.LENGTH_SHORT).show();
                                        pd.dismiss();
                                    }
                                    catch(Exception ex){
                                        ex.printStackTrace();
                                    }

                                }
                            });
                        }

                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                try {
                    Toast.makeText(add.this,
                            "fail to upload"+e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                try {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred())
                            / taskSnapshot.getTotalByteCount();
                    pd.setProgress((int) progress);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
        {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectfile();
            }
            else {
                Toast.makeText(add.this, "please provide permission", Toast.LENGTH_SHORT).
                        show();
            }

        }

    private void selectfile() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/mpeg");
        startActivityForResult(Intent.createChooser(intent, ""), 86);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == Activity.RESULT_OK ){
            try {
                assert data != null;
                u = data.getData();
                trackname.setText(u.getLastPathSegment());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}