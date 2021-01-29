package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Place {
    private String placeName;
    private List<ExactPlace> exactPlaceList = new ArrayList<>();

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public List<ExactPlace> getExactPlaceList() {
        return exactPlaceList;
    }

    public void setExactPlaceList(List<ExactPlace> exactPlaceList) {
        this.exactPlaceList = exactPlaceList;
    }
}
