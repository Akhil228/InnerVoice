package com.example.innervoice2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class songpage extends AppCompatActivity {
    TextView heading,lyricscroll;
    Button mplay, pause,stop,record,stoprecord;
    String audiourl,pathsave="";
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    final int REQUEST_PERMISSION_CODE=1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songpage);
        heading= findViewById(R.id.title);
        lyricscroll=findViewById(R.id.lyricsscroll);
        mplay=findViewById(R.id.play);
        pause=findViewById(R.id.pausebutton);
        stop=findViewById(R.id.stop);
        record=findViewById(R.id.record);
        stoprecord=findViewById(R.id.stoprecord);

        stoprecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                record.setEnabled(true);
                stoprecord.setEnabled(false);
                try {
                    mediaRecorder.stop();

                }catch (IllegalStateException ex){
                    ex.getStackTrace();
                }
                catch (Exception ex){
                    ex.getStackTrace();
                }
                try {
                    mediaRecorder.release();
                    mediaRecorder.reset();
                }catch (IllegalStateException ex){
                    ex.getStackTrace();
                }
                catch (Exception ex){
                    ex.getStackTrace();
                }
                Toast.makeText(songpage.this, "recorded file is saved", Toast.LENGTH_LONG).show();
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicrecord();
            }
        });
        mplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermissionFrmDevice()) {
                    mplay.setEnabled(false);
                    stop.setEnabled(true);
                    pause.setEnabled(true);
                    playandrecord();
                }
                else{
                    requestPermision();
                }
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer.pause();
                }catch (Exception ex){
                    ex.getStackTrace();
                }
                mplay.setEnabled(true);
                stop.setEnabled(true);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer.stop();
                }catch (Exception ex){
                    ex.getStackTrace();

                }
                    mplay.setEnabled(true);
                    stop.setEnabled(true);


            }
        });
        if(!checkPermissionFrmDevice())
            requestPermision();
    }

    private void musicrecord() {
        try {

            pathsave = Environment.getExternalStorageDirectory().getAbsolutePath()+"/*"+toString()+"/audiorecordtest.3gp";
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();
            mediaRecorder.setOutputFile(pathsave);

        }catch (Exception ex){
            ex.getStackTrace();
        }
        record.setEnabled(false);
        stoprecord.setEnabled(true);
    }

    private void requestPermision() {
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        },REQUEST_PERMISSION_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         switch(requestCode){
             case  REQUEST_PERMISSION_CODE:
             {
                 if(grantResults.length >0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED)
                     Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
             else
                     Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();

             }
                  break;
         }

    }

    private boolean checkPermissionFrmDevice() {
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result=ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO);
        return write_external_storage_result== PackageManager.PERMISSION_GRANTED &&
                record_audio_result==PackageManager.PERMISSION_GRANTED;
    }


    private void playandrecord() {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(audiourl);

            } catch (IllegalArgumentException e) {

                // TODO Auto-generated catch block
                e.printStackTrace();

            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }

            try {
                mediaPlayer.prepare();
                mediaPlayer.start();


            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i= getIntent();
        String title =i.getStringExtra("title");
        heading.setText(title);
        String lyricsspace=i.getStringExtra("lyricsspace");
        lyricscroll.setText(lyricsspace);
        audiourl=i.getStringExtra("songspace");

    }

}