package com.andersenlab.patternsSample.entity;

import java.util.Date;

public abstract class Literature {

    protected long id;
    protected String title;
    protected PublishingHouse publishingHouse;
    protected Date publishDate;

    public Literature(LiteratureBuilder<?, ?> builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.publishingHouse = builder.publishingHouse;
        this.publishDate = builder.publishDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
