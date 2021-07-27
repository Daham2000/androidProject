package com.example.demoproject.model;

import java.util.ArrayList;

public class MagnetometerModel {

    private ArrayList<MagnetometerModel.Magnetometer> magnetometerArrayList;

    public ArrayList<Magnetometer> getMagnetometerArrayList() {
        return magnetometerArrayList;
    }

    public void setMagnetometerArrayList(ArrayList<Magnetometer> magnetometerArrayList) {
        this.magnetometerArrayList = magnetometerArrayList;
    }

    public static class Magnetometer {
        private float MagnetoX;
        private float MagnetoY;
        private float MagnetoZ;

        public float getMagnetoX() {
            return MagnetoX;
        }

        public void setMagnetoX(float magnetoX) {
            MagnetoX = magnetoX;
        }

        public float getMagnetoY() {
            return MagnetoY;
        }

        public void setMagnetoY(float magnetoY) {
            MagnetoY = magnetoY;
        }

        public float getMagnetoZ() {
            return MagnetoZ;
        }

        public void setMagnetoZ(float magnetoZ) {
            MagnetoZ = magnetoZ;
        }
    }

}
