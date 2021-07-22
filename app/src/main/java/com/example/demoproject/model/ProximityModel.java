package com.example.demoproject.model;

import java.util.ArrayList;
import java.util.HashSet;

public class ProximityModel {

    private ArrayList<ProximityModel.Proximity> proximityModelList;

    public ArrayList<Proximity> getProximityModelList() {
        return proximityModelList;
    }

    public void setProximityModelList(ArrayList<ProximityModel.Proximity> proximityModelList) {
        this.proximityModelList = proximityModelList;
    }

    public static class Proximity {
        private float proximity;

        public float getProximity() {
            return proximity;
        }

        public void setProximity(float proximity) {
            this.proximity = proximity;
        }
    }
}
