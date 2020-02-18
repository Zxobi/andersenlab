package com.andersenlab.patternsSample.entity;

public class MagazineBuilder extends LiteratureBuilder<Magazine, MagazineBuilder> {
    protected int serialNumber;

    public MagazineBuilder setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    @Override
    public Magazine build() {
        return new Magazine(this);
    }
}
