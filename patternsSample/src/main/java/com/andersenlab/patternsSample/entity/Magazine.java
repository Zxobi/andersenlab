package com.andersenlab.patternsSample.entity;

public class Magazine extends Literature {

    private int serialNumber;

    public Magazine(Builder builder) {
        super(builder);
        this.serialNumber = builder.serialNumber;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public static class Builder extends Literature.Builder<Builder> {

        int serialNumber;

        public Builder() {

        }

        public Builder setSerialNumber(int serialNumber) {
            this.serialNumber = serialNumber;
            return self();
        }

        @Override
        public Magazine build() {
            return new Magazine(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "id=" + id +
                ", serialNumber=" + serialNumber +
                ", title='" + title + '\'' +
                ", publishingHouse='" + publishingHouse.getName() + '\'' +
                ", publishDate=" + publishDate +
                '}';
    }
}
