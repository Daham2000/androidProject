package com.example.demoproject.model;

import java.util.ArrayList;

public class PressureModel {
    private ArrayList<PressureModel.Pressure> pressureArrayList;

    public ArrayList<Pressure> getPressureArrayList() {
        return pressureArrayList;
    }

    public void setPressureArrayList(ArrayList<Pressure> pressureArrayList) {
        this.pressureArrayList = pressureArrayList;
    }

    public static class Pressure {
        private float pressure;

        public float getPressure() {
            return pressure;
        }

        public void setPressure(float pressure) {
            this.pressure = pressure;
        }
    }
}
