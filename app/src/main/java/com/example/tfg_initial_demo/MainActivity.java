package com.example.tfg_initial_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{

    private EditText locutorNameBox;
    private TextView subTitleBox;
    private GestureDetector gestureDetector;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locutorNameBox = findViewById(R.id.locutorNameBox);
        subTitleBox = findViewById(R.id.subTitleBox);

        count = 0;

        this.gestureDetector =new GestureDetector(this, (GestureDetector.OnGestureListener) this);
    }

    public void createButton(View view){

        String locutorName = locutorNameBox.getText().toString();

        if(locutorName.isEmpty())
            Toast.makeText(this, "Introduzca el nombre, por favor.", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "Creando ejemplo para " + locutorName + "...", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, ASRActivity.class);

            i.putExtra("inputName",locutorName);

            startActivity(i);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        count = count + 1;
        subTitleBox.setText(Integer.toString(count));

        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        count = count + 2;
        subTitleBox.setText(Integer.toString(count));
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }


}