package com.example.demoproject;

import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demoproject.sensor.AccelerometerSensorManage;
import com.example.demoproject.sensor.GyroscopeSensorManage;
import com.example.demoproject.sensor.HumiditySensorManage;
import com.example.demoproject.sensor.LightSensorManage;
import com.example.demoproject.sensor.MagnetometerSensorManage;
import com.example.demoproject.sensor.PressureSensorManage;
import com.example.demoproject.sensor.ProximitySensorManage;
import com.example.demoproject.sensor.TempSensorManage;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);
        //initialize texView values
        TextView xValue = rootView.findViewById(R.id.xValue);
        Log.d("TAG", "onCreateView: "+xValue);
        TextView yValue =  rootView.findViewById(R.id.yValue);
        TextView zValue =  rootView.findViewById(R.id.zValue);

        TextView gyXValue =  rootView.findViewById(R.id.xGyroValue);
        TextView gYValue =  rootView.findViewById(R.id.yGyroValue);
        TextView gyZValue =  rootView.findViewById(R.id.zGyroValue);

        TextView maXValue =  rootView.findViewById(R.id.xMagnetoMeterValue);
        TextView maYValue =  rootView.findViewById(R.id.yMagnetoMeterValue);
        TextView maZValue =  rootView.findViewById(R.id.zMagnetoMeterValue);

        TextView lightValue =  rootView.findViewById(R.id.light);
        TextView pressureValue =  rootView.findViewById(R.id.pressure);
        TextView tempValue =  rootView.findViewById(R.id.temp);
        TextView humidityValue =  rootView.findViewById(R.id.humidity);
        TextView proximityValue =  rootView.findViewById(R.id.proximity);

        //initialize sensor manager
        final SensorManager sensorManager = (SensorManager) getContext().getSystemService(getContext().SENSOR_SERVICE);
        if(xValue!=null){
            //get the sensor accelerometer
            AccelerometerSensorManage accelerometerSensor = new AccelerometerSensorManage(
                    sensorManager, xValue, yValue, zValue, getContext());
            //get the sensor Gyroscope
            GyroscopeSensorManage gyroscopeSensorManage = new GyroscopeSensorManage(
                    sensorManager, gyXValue, gYValue, gyZValue);
            //get the sensor Magnetometer
            MagnetometerSensorManage magnetometerSensorManage = new MagnetometerSensorManage(
                    sensorManager, maXValue, maYValue, maZValue);
            //get the sensor Light
            LightSensorManage lightSensorManage = new LightSensorManage(
                    sensorManager, lightValue);
            //get the Pressure
            PressureSensorManage pressureSensorManage = new PressureSensorManage(
                    sensorManager, pressureValue);
            //get the Temperature sensor
            TempSensorManage tempSensorManage = new TempSensorManage(
                    sensorManager, tempValue);
            //get the Humidity sensor
            HumiditySensorManage humiditySensorManage = new HumiditySensorManage(
                    sensorManager, humidityValue);
            //get the proximity sensor
            ProximitySensorManage proximitySensorManage = new ProximitySensorManage(
                    sensorManager, proximityValue);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

    }
}