package com.example.speechtotext;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView tvSpeechToText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        ImageView ivMic = findViewById(R.id.iv_mic);
        tvSpeechToText = findViewById(R.id.tv_speech_to_text);

        // Set click listener for the microphone icon
        ivMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start speech recognition when the microphone icon is clicked
                startSpeechRecognition();
            }
        });
    }
    private void startSpeechRecognition() {
        // Create an Intent for speech recognition
       Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

        try {
            // Launch the speech recognition activity
           speechRecognitionLauncher.launch(intent);
        } catch (ActivityNotFoundException e) {
            // Handle the case where speech recognition is not supported
            Toast.makeText(MainActivity.this, "Speech recognition not supported on this device.", Toast.LENGTH_SHORT).show();
        }
    }


    // Activity result launcher for speech recognition
    // Define an ActivityResultLauncher to handle speech recognition results



    private final ActivityResultLauncher<Intent> speechRecognitionLauncher = registerForActivityResult(
            // Create a new instance of ActivityResultContracts.StartActivityForResult
            new ActivityResultContracts.StartActivityForResult(),
            // Define an anonymous inner class implementing ActivityResultCallback<ActivityResult>
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Check if the result code indicates success and data is not null
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        // Retrieve the recognized speech results as an ArrayList of strings
                        ArrayList<String> resultData = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        if (resultData != null && !resultData.isEmpty()) {
                            // Display the recognized text in a TextView
                            tvSpeechToText.setText(resultData.get(0));
                        }
                    }
                }
            }
    );

}


