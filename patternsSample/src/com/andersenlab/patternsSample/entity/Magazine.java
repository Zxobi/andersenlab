package com.andersenlab.patternsSample.entity;

public class Magazine extends Literature {

    private int serialNumber;

    public Magazine(MagazineBuilder builder) {
        super(builder);
        this.serialNumber = builder.serialNumber;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public static MagazineBuilder newBuilder() {
        return new MagazineBuilder();
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "serialNumber=" + serialNumber +
                ", title='" + title + '\'' +
                ", publishingHouse='" + publishingHouse.getName() + '\'' +
                ", publishDate=" + publishDate +
                '}';
    }
}
