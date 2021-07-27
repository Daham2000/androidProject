package com.example.demoproject.model;

import java.util.ArrayList;

public class LightModel {

    private ArrayList<LightModel.Light> lightArrayList;

    public ArrayList<Light> getLightArrayList() {
        return lightArrayList;
    }

    public void setLightArrayList(ArrayList<Light> lightArrayList) {
        this.lightArrayList = lightArrayList;
    }

    public static class Light {
        private float light;

        public float getLight() {
            return light;
        }

        public void setLight(float light) {
            this.light = light;
        }
    }
}
