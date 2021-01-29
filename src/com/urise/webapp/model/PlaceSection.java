package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class PlaceSection {
    private List<Place> placeList = new ArrayList<>();

    public List<Place> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<Place> placeList) {
        this.placeList = placeList;
    }
}
