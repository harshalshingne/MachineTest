package com.wednesday.machinetest.model;

public class Artists {

    private String wrapperType;
    private String kind;
    private String artistId;

    public String getWrapperType() {
        return wrapperType;
    }

    public void setWrapperType(String wrapperType) {
        this.wrapperType = wrapperType;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    @Override
    public String toString() {
        return "Artists{" +
                "wrapperType='" + wrapperType + '\'' +
                ", kind='" + kind + '\'' +
                ", artistId='" + artistId + '\'' +
                '}';
    }
}
