package com.andersenlab.bookstorage.util;

import com.andersenlab.bookstorage.entity.PublishingHouse;

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

    public PublishingHouse getCurPublishingHouse() {
        return curPublishingHouse;
    }

    public void setCurPublishingHouse(PublishingHouse curPublishingHouse) {
        this.curPublishingHouse = curPublishingHouse;
    }
}
