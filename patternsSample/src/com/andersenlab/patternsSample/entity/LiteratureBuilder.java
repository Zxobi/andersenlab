package com.andersenlab.patternsSample.entity;

import java.util.Date;

public abstract class LiteratureBuilder<ST extends Literature, B extends LiteratureBuilder<ST, B>> {

    protected String title;
    protected PublishingHouse publishingHouse;
    protected Date publishDate;

    public B setTitle(String title) {
        this.title = title;
        return (B)this;
    }

    public B setPublishingHouse(PublishingHouse publishingHouse) {
        this.publishingHouse = publishingHouse;
        return (B)this;
    }

    public B setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
        return (B)this;
    }

    abstract public ST build();
}
