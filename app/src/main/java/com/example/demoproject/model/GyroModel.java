package com.example.demoproject.model;

import java.util.ArrayList;

public class GyroModel {
    private ArrayList<GyroModel.Gyroscope> gyroscopeArrayList;

    public ArrayList<Gyroscope> getGyroscopeArrayList() {
        return gyroscopeArrayList;
    }

    public void setGyroscopeArrayList(ArrayList<Gyroscope> gyroscopeArrayList) {
        this.gyroscopeArrayList = gyroscopeArrayList;
    }

    public static class Gyroscope {
        private float gyroscopeX;
        private float gyroscopeY;
        private float gyroscopeZ;

        public float getGyroscopeX() {
            return gyroscopeX;
        }

        public void setGyroscopeX(float gyroscopeX) {
            this.gyroscopeX = gyroscopeX;
        }

        public float getGyroscopeY() {
            return gyroscopeY;
        }

        public void setGyroscopeY(float gyroscopeY) {
            this.gyroscopeY = gyroscopeY;
        }

        public float getGyroscopeZ() {
            return gyroscopeZ;
        }

        public void setGyroscopeZ(float gyroscopeZ) {
            this.gyroscopeZ = gyroscopeZ;
        }
    }
}
