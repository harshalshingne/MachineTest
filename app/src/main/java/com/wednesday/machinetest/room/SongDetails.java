package com.wednesday.machinetest.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class SongDetails implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "CollectionCensoredName")
    private String CollectionCensoredName;

    @ColumnInfo(name = "CollectionArtistName")
    private String CollectionArtistName;

    @ColumnInfo(name = "ArtworkUrl100")
    private String ArtworkUrl100;

    @ColumnInfo(name = "previewUrl")
    private String previewUrl;

    @ColumnInfo(name = "finished")
    private boolean finished;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCollectionCensoredName() {
        return CollectionCensoredName;
    }

    public void setCollectionCensoredName(String collectionCensoredName) {
        CollectionCensoredName = collectionCensoredName;
    }

    public String getCollectionArtistName() {
        return CollectionArtistName;
    }

    public void setCollectionArtistName(String collectionArtistName) {
        CollectionArtistName = collectionArtistName;
    }

    public String getArtworkUrl100() {
        return ArtworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        ArtworkUrl100 = artworkUrl100;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }
}
