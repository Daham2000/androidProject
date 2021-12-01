package com.example.demoproject.model;

import java.util.List;

public class DateTimeModel {
    private List<String> lastDataUploadTimeList;

    public DateTimeModel() {
    }

    public DateTimeModel(List<String> lastDataUploadTimeList) {
        this.lastDataUploadTimeList = lastDataUploadTimeList;
    }

    public List<String> getLastDataUploadTimeList() {
        return lastDataUploadTimeList;
    }

    public void setLastDataUploadTimeList(List<String> lastDataUploadTimeList) {
        this.lastDataUploadTimeList = lastDataUploadTimeList;
    }
}
