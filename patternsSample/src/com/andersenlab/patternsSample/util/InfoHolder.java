package com.andersenlab.patternsSample.util;

import com.andersenlab.patternsSample.entity.PublishingHouse;

import java.util.ArrayList;
import java.util.List;

public class InfoHolder {

    private static InfoHolder instance;

    private PublishingHouse curPublishingHouse;

    private List<PublishingHouse> publishingHouses = new ArrayList<>();

    private InfoHolder() {}

    public static InfoHolder getInstance() {
        if (instance == null) {
            instance = new InfoHolder();
        }

        return instance;
    }

    public void addPublishingHouse(PublishingHouse publishingHouse) {
        publishingHouses.add(publishingHouse);
    }

    public List<PublishingHouse> getPublishingHouses() {
        return publishingHouses;
    }

    public PublishingHouse getCurPublishingHouse() {
        return curPublishingHouse;
    }

    public void setCurPublishingHouse(PublishingHouse curPublishingHouse) {
        this.curPublishingHouse = curPublishingHouse;
    }
}
