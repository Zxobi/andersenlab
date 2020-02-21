package com.andersenlab.patternsSample.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public abstract class Literature {

    @Id
    @GeneratedValue()
    @Column(name = "id", nullable = false, updatable = false)
    protected long id;
    @Column(name = "title")
    protected String title;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publish_house_id", referencedColumnName = "id")
    protected PublishingHouse publishingHouse;
    @Column(name = "publish_date")
    protected Date publishDate;

    protected Literature() {

    }

    public Literature(Builder<?> builder) {
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
}
