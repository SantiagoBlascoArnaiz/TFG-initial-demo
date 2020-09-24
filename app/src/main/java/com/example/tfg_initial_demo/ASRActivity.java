package com.example.tfg_initial_demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ASRActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    private TextView locutorBox, textToBox;
    private MediaRecorder recorder;
    private String voiceFile;
    private Button recordButton, playButton;

    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_s_r);

        locutorBox = findViewById(R.id.locutorNameBox);
        textToBox = findViewById(R.id.textToBox);


        Bundle inputName = getIntent().getExtras();

        String locutorName = inputName.getString("inputName");

        locutorBox.setText(locutorName + " ha dicho:");

        recordButton = findViewById(R.id.recordB);

        playButton = findViewById(R.id.playB);

        playButton.setEnabled(false);

        checkPermissions();
    }

    public void speechToText(View view){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Di algo");

        try {

            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);

        } catch (Exception e){

            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{

                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                textToBox.setText(result.get(0));
            }
            break;
        }
    }

    public void record(View view) {

        if(isRecording){

            isRecording = false;
            recordButton.setText("Grabar");

            Log.d("SANTAIGO", "GO TO STOP");
            recorder.stop();
            Log.d("SANTAIGO", "STOPED");
            recorder.release();
            recorder = null;
            Log.d("SANTAIGO", "AHORA ES NULL");
            playButton.setEnabled(true);

        } else {
            isRecording = true;
            recordButton.setText("Finalizar");

            Toast.makeText(this, "Grabando voz...", Toast.LENGTH_SHORT).show();

            String recordPath = ASRActivity.this.getExternalFilesDir("/").getAbsolutePath();
            voiceFile = "filename.3gp";

            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(recordPath + "/" + voiceFile);

            try {
                recorder.prepare();
            } catch (IOException e){
                e.printStackTrace();
            }

            recorder.start();
        }
    }

    public void play(View view){

        playButton.setEnabled(false);

        String recordPath = ASRActivity.this.getExternalFilesDir("/").getAbsolutePath();
        voiceFile = "filename.3gp";

        MediaPlayer mediaPLayer = new MediaPlayer();
        try{

            mediaPLayer.setDataSource(recordPath + "/" + voiceFile);
            mediaPLayer.prepare();



        }catch (IOException e){
        }

        mediaPLayer.start();
        Toast.makeText(this, "Reproduciendo audio", Toast.LENGTH_SHORT).show();

        playButton.setEnabled(true);
    }

    private void checkPermissions(){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ASRActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }
    }

}
