package com.example.flashlight;

import android.annotation.SuppressLint
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton toggleButton;
    boolean hasCameraFlash =false;
    boolean flashOn=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggleButton = findViewById(R.id.toggleButton);
        hasCameraFlash= getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        toggleButton.setOnClickListener(v -> {
            if(hasCameraFlash){
                if(flashOn){
                    flashOn=false;
                    toggleButton.setImageResource(R.drawable.toggle_off_2);
                    try {
                        flashLightOff();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    flashOn=true;
                    toggleButton.setImageResource(R.drawable.toggle_on_svgrepo_com__2_);
                    try {
                        flashLightOn();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            else{
                Toast.makeText(MainActivity.this,"No flash available on your device.",Toast.LENGTH_LONG).show();
            }

        });

    }
    @SuppressLint("ObsoleteSdkInt")
    private void flashLightOn() throws CameraAccessException{
        CameraManager cameraManager=(CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId=cameraManager.getCameraIdList()[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cameraManager.setTorchMode(cameraId,true);
            Toast.makeText(MainActivity.this,"Flash Light is ON ",Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressLint("ObsoleteSdkInt")
    private void flashLightOff() throws CameraAccessException{
        CameraManager cameraManager=(CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId=cameraManager.getCameraIdList()[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cameraManager.setTorchMode(cameraId,false);
            Toast.makeText(MainActivity.this,"Flash Light is OFF",Toast.LENGTH_SHORT).show();
        }
    }
}