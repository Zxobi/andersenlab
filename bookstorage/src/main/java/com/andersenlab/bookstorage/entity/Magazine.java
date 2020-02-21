package com.andersenlab.bookstorage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Magazine extends Literature {

    @Column(name = "serial_number")
    private int serialNumber;

    protected Magazine() {

    }

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
