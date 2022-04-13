package com.example.teste2handson;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teste2handson.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    final String tag = "MainActivity";
    private ActivityMainBinding binding;
    SensorManager mSensorManager = null;
    Sensor accelerometerSensor;
    Sensor rotationSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // get reference to SensorManager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        rotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(this, rotationSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, accelerometerSensor);
        mSensorManager.unregisterListener(this, rotationSensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(tag, "onSensorChanged: " + event.sensor.getStringType() + ", x: " +
                event.values[0] + ", y: " + event.values[1] + ", z: " + event.values[2]);
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            binding.rotationX.setText("Rotation X: " + event.values[0]);
            binding.rotationY.setText("Rotation Y: " + event.values[1]);
            binding.rotationZ.setText("Rotation Z: " + event.values[2]);
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            binding.accelerometerX.setText("Accel X: " + event.values[0]);
            binding.accelerometerY.setText("Accel Y: " + event.values[1]);
            binding.accelerometerZ.setText("Accel Z: " + event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(tag,"onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
    }
}