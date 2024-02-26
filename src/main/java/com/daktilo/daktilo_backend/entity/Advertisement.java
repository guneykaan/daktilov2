package com.daktilo.daktilo_backend.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity(name = "advertisement")
public class Advertisement {

    @Id
    @Column(name="advertisement_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID advertisementId;

    @Column(name = "header")
    private String header;

    @Column(name = "ad_url")
    private String adUrl;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name="place")
    private String place;

    @Column(name="is_active")
    private boolean isActive;

    public UUID getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(UUID advertisementId) {
        this.advertisementId = advertisementId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advertisement that = (Advertisement) o;
        return isActive == that.isActive && Objects.equals(advertisementId, that.advertisementId) && Objects.equals(header, that.header) && Objects.equals(adUrl, that.adUrl) && Objects.equals(photoUrl, that.photoUrl) && Objects.equals(place, that.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(advertisementId, header, adUrl, photoUrl, place, isActive);
    }
}
