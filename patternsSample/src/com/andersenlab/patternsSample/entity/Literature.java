package com.andersenlab.patternsSample.entity;

import java.util.Date;

public abstract class Literature {

    protected String title;
    protected PublishingHouse publishingHouse;
    protected Date publishDate;

    public Literature(LiteratureBuilder builder) {
        this.title = builder.title;
        this.publishingHouse = builder.publishingHouse;
        this.publishDate = builder.publishDate;
    }

    public String getTitle() {
        return title;
    }

    public PublishingHouse getPublishingHouse() {
        return publishingHouse;
    }

    public Date getPublishDate() {
        return publishDate;
    }
}
