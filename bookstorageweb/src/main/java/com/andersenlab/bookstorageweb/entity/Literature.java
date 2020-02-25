package com.andersenlab.bookstorageweb.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import java.util.Date;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Book.class, name = "book"),
        @JsonSubTypes.Type(value = Magazine.class, name = "magazine")
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Literature")
public abstract class Literature {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    protected long id;
    @Column(name = "title", nullable = false)
    protected String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publish_house_id", referencedColumnName = "id", updatable = false)
    protected PublishingHouse publishingHouse;
    @Column(name = "publish_date", nullable = false, updatable = false)
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
