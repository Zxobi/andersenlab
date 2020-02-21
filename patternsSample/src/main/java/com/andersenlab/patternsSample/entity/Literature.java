package com.andersenlab.patternsSample.entity;

import java.util.Date;

public abstract class Literature {

    protected long id;
    protected String title;
    protected PublishingHouse publishingHouse;
    protected Date publishDate;

    public Literature(Builder<?> builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.publishingHouse = builder.publishingHouse;
        this.publishDate = builder.publishDate;
    }

    public static abstract class Builder<B extends Builder<B>> {
        protected long id;
        protected String title;
        protected PublishingHouse publishingHouse;
        protected Date publishDate;

        public B setId(long id) {
            this.id = id;
            return self();
        }

        public B setTitle(String title) {
            this.title = title;
            return self();
        }

        public B setPublishingHouse(PublishingHouse publishingHouse) {
            this.publishingHouse = publishingHouse;
            return self();
        }

        public B setPublishDate(Date publishDate) {
            this.publishDate = publishDate;
            return self();
        }

        public abstract Literature build();

        protected abstract B self();
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
